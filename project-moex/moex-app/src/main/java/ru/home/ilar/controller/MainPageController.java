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
import ru.home.ilar.dto.UserSettingsItemDto;
import ru.home.ilar.dto.StockEntryDto;
import ru.home.ilar.dto.StockSettingsDto;
import ru.home.ilar.service.MoexInformationService;
import ru.home.ilar.service.MoexIntegrationService;
import ru.home.ilar.service.UserSettingsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MoexIntegrationService moexIntegrationService;

    private final UserSettingsService userSettingsService;

    private final MoexInformationService moexInformationService;

    @GetMapping(value = "/api/stockSettings", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<StockSettingsDto> getStockSettings(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Mono<List<StockEntryDto>> stock = moexIntegrationService.getCurrentStockIndex();
        Flux<UserSettingsItemDto> settings = userSettingsService.getUserSettings(userId);
        return Mono
                .zip(stock, settings.collectList())
                .map(tuple -> new StockSettingsDto(tuple.getT1(), tuple.getT2()));
    }

    @GetMapping(value = "/api/info", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<InformationDto> info() {
        return moexInformationService.getInformation();
    }

    @PostMapping(value = "/api/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> saveUserSettings(@RequestBody List<UserSettingsItemDto> userSettingsItems,
                                                    Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return userSettingsService.saveUserSettings(userId, userSettingsItems)
                .then(Mono.just(new ResponseEntity<>("{}", HttpStatus.OK)));
    }

}
