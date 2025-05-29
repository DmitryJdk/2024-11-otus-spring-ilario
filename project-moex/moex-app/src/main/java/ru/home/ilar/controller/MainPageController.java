package ru.home.ilar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.home.ilar.dto.InformationDto;
import ru.home.ilar.dto.UserIndexModificationItemDto;
import ru.home.ilar.dto.StockEntryDto;
import ru.home.ilar.dto.StockSettingsDto;
import ru.home.ilar.service.InformationService;
import ru.home.ilar.service.StockIntegrationService;
import ru.home.ilar.service.UserIndexModificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final StockIntegrationService stockIntegrationService;

    private final UserIndexModificationService userIndexModificationService;

    private final InformationService informationService;

    @GetMapping(value = "/api/stockSettings", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<StockSettingsDto> getStockSettings(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Mono<List<StockEntryDto>> stock = stockIntegrationService.getCurrentStockIndex();
        Flux<UserIndexModificationItemDto> modifications = userIndexModificationService
                .getUserIndexModifications(userId);
        return Mono
                .zip(stock, modifications.collectList())
                .map(tuple -> new StockSettingsDto(tuple.getT1(), tuple.getT2()));
    }

    @GetMapping(value = "/api/info", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<InformationDto> info() {
        return informationService.getInformation();
    }

    @PostMapping(value = "/api/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> saveUserModifications(
            @RequestBody List<UserIndexModificationItemDto> userIndexModifications,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();
        return userIndexModificationService.saveUserIndexModifications(userId, userIndexModifications)
                .then(Mono.just(new ResponseEntity<>("{}", HttpStatus.OK)));
    }

}
