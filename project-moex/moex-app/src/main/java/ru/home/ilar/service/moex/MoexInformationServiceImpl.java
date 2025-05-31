package ru.home.ilar.service.moex;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.home.ilar.dto.InformationDto;
import ru.home.ilar.service.InformationService;

@Service
@RequiredArgsConstructor
public class MoexInformationServiceImpl implements InformationService {

    private final WebClient moexInformationClient;

    @Override
    public Flux<InformationDto> getInformation() {
        return moexInformationClient
                .get()
                .uri("/api/info")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .map(InformationDto::new);
    }
}
