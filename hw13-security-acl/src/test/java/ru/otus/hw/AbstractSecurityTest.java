package ru.otus.hw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.security.configuration.SecurityConfiguration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import(SecurityConfiguration.class)
public abstract class AbstractSecurityTest {

    @Autowired
    protected MockMvc mvc;

    protected void checkRequest(HttpMethod method,
                                String url,
                                String user,
                                String request,
                                Integer statusCode,
                                String redirectTo) throws Exception {
        MockHttpServletRequestBuilder builder = prepareBuilder(method, url, user, request);
        ResultActions result = mvc.perform(builder)
                .andExpect(status().is(statusCode));
        if (user == null) {
            result.andExpect(redirectedUrlPattern("**/login"));
        } else if (redirectTo != null) {
            result.andExpect(redirectedUrl(redirectTo));
        }
    }

    private MockHttpServletRequestBuilder prepareBuilder(HttpMethod method, String url, String user, String request) {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request(method, url);
        if (user != null) {
            builder.with(user(user));
        }
        if (request != null) {
            builder.contentType("application/x-www-form-urlencoded");
            builder.content(request);
        }
        return builder;
    }
}
