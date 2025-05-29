package ru.home.ilar.moex;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@ActiveProfiles("TEST")
@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(name = "moex", port = 8089)
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MoexAdapterTest {

    @Autowired
    public FeignMoexAdapter adapter;

    @MockitoSpyBean
    @Qualifier("moexCache")
    public Cache<String, String> moexCache;

    private static final String MOEX_PATH = "/statistics/engines/stock/markets/index/analytics/IMOEX.html?limit=100";

    @Test
    @DisplayName("должен ответить успешно после двух попыток с ошибкой")
    void shouldRetryOnFailureAndSucceed() {
        stubFor(get(urlEqualTo(MOEX_PATH))
                .inScenario("Retry scenario")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse().withStatus(500))
                .willSetStateTo("Second attempt")
        );

        stubFor(get(urlEqualTo(MOEX_PATH))
                .inScenario("Retry scenario")
                .whenScenarioStateIs("Second attempt")
                .willReturn(aResponse().withStatus(500))
                .willSetStateTo("Third attempt"));

        stubFor(get(urlEqualTo(MOEX_PATH))
                .inScenario("Retry scenario")
                .whenScenarioStateIs("Third attempt")
                .willReturn(aResponse().withBody("success")));

        String result = adapter.getCurrentIndexState();
        assertEquals("success", result);

        verify(3, getRequestedFor(urlEqualTo(MOEX_PATH)));
    }

    @Test
    @DisplayName("должен ответить ошибкой, если запросов больше 3")
    void shouldLimitRateOfRequests() {
        //возвращаем ошибку, чтобы избежать кэширования
        stubFor(get(urlEqualTo(MOEX_PATH))
                .willReturn(aResponse().withStatus(500)));

        assertThrows(Exception.class, () -> adapter.getCurrentIndexState());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> adapter.getCurrentIndexState());
        assertTrue(ex.getMessage().contains("RateLimiter"));
    }

    @Test
    @DisplayName("должен открыть CB, если проваленных запросов больше 5")
    void shouldOpenCircuitBreakerAfterFailures() {
        stubFor(get(urlEqualTo(MOEX_PATH))
                .willReturn(aResponse().withStatus(500)));
        //3 попытки будут из-за retry
        assertThrows(Exception.class, () -> adapter.getCurrentIndexState());
        //4-ая попытку откинет RL
        assertThrows(Exception.class, () -> adapter.getCurrentIndexState());
        //5-ая попытка откинет RL
        assertThrows(Exception.class, () -> adapter.getCurrentIndexState());

        //6-ая попытка должен открыться CB
        RuntimeException ex = assertThrows(RuntimeException.class, () -> adapter.getCurrentIndexState());
        assertTrue(ex.getMessage().contains("CircuitBreaker"));
    }

    @Test
    @DisplayName("должен ответить успешно, если в кэше есть данные")
    void shouldSucceedIfDataPresentInCache() {
        stubFor(get(urlEqualTo(MOEX_PATH))
                .inScenario("Cache scenario")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse().withBody("success"))
                .willSetStateTo("Failure state")
        );

        stubFor(get(urlEqualTo(MOEX_PATH))
                .inScenario("Cache scenario")
                .whenScenarioStateIs("Failure state")
                .willReturn(aResponse().withStatus(500))
                .willSetStateTo("Failure state")
        );

        String result = adapter.getCurrentIndexState();
        assertEquals("success", result);

        String resultWhenServiceUnavailable = adapter.getCurrentIndexState();
        assertEquals("success", resultWhenServiceUnavailable);

        verify(2, getRequestedFor(urlEqualTo(MOEX_PATH)));
        Mockito.verify(moexCache, times(1)).getIfPresent("currentIndex");
    }
}
