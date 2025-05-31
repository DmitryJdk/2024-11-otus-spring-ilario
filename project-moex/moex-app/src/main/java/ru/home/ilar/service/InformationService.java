package ru.home.ilar.service;

import reactor.core.publisher.Flux;
import ru.home.ilar.dto.InformationDto;

public interface InformationService {

    Flux<InformationDto> getInformation();
}
