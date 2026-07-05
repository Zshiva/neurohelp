package com.project.neurohelp.controllers;

import com.project.neurohelp.controllers.converter.WellnessConverter;
import com.project.neurohelp.controllers.payload.wellness.CrisisResourcesResponsePayload;
import com.project.neurohelp.controllers.payload.wellness.WellnessCheckInRequestPayload;
import com.project.neurohelp.controllers.payload.wellness.WellnessCheckInResponsePayload;
import com.project.neurohelp.platform.rest.RestResponse;
import com.project.neurohelp.usecases.wellness.checkin.WellnessCheckInUseCase;
import com.project.neurohelp.usecases.wellness.resources.CrisisResourcesUseCase;
import com.project.neurohelp.usecases.wellness.resources.CrisisResourcesUseCaseRequest;
import com.project.neurohelp.usecases.wellness.resources.CrisisResourcesUseCaseRequestBuilder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/neurohelp/api/v1/wellness")
@Slf4j
public class WellnessController {

    private final WellnessCheckInUseCase wellnessCheckInUseCase;
    private final CrisisResourcesUseCase crisisResourcesUseCase;

    public WellnessController(WellnessCheckInUseCase wellnessCheckInUseCase,
                              CrisisResourcesUseCase crisisResourcesUseCase) {
        this.wellnessCheckInUseCase = wellnessCheckInUseCase;
        this.crisisResourcesUseCase = crisisResourcesUseCase;
    }

    @PostMapping("/check-in")
    public ResponseEntity<RestResponse<WellnessCheckInResponsePayload>> checkIn(@Valid @RequestBody WellnessCheckInRequestPayload payload) {
        log.debug("Incoming wellness check-in request: sessionId={}, country={}, locale={}", payload.sessionId(), payload.country(), payload.locale());
        var response = wellnessCheckInUseCase.execute(WellnessConverter.toCheckInRequest(payload));
        return ResponseEntity.ok(RestResponse.success(WellnessConverter.toCheckInPayload(response.orElseThrow())));
    }

    @GetMapping("/crisis-resources")
    public ResponseEntity<RestResponse<CrisisResourcesResponsePayload>> crisisResources(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String locale
    ) {
        var response = crisisResourcesUseCase.execute(CrisisResourcesUseCaseRequestBuilder.builder()
                .country(country)
                .locale(locale)
                .build());
        return ResponseEntity.ok(RestResponse.success(WellnessConverter.toCrisisResourcesPayload(response.orElseThrow())));
    }
}

