package com.project.neurohelp.controllers.converter;

import com.project.neurohelp.controllers.payload.wellness.CrisisResourcesResponsePayload;
import com.project.neurohelp.controllers.payload.wellness.CrisisResourcesResponsePayloadBuilder;
import com.project.neurohelp.controllers.payload.wellness.CrisisResourcesResponsePayloadCrisisResourcePayloadBuilder;
import com.project.neurohelp.controllers.payload.wellness.WellnessCheckInRequestPayload;
import com.project.neurohelp.controllers.payload.wellness.WellnessCheckInResponsePayload;
import com.project.neurohelp.controllers.payload.wellness.WellnessCheckInResponsePayloadBuilder;
import com.project.neurohelp.controllers.payload.wellness.WellnessCheckInResponsePayloadCrisisResourcePayloadBuilder;
import com.project.neurohelp.usecases.wellness.checkin.WellnessCheckInUseCaseRequest;
import com.project.neurohelp.usecases.wellness.checkin.WellnessCheckInUseCaseRequestBuilder;
import com.project.neurohelp.usecases.wellness.checkin.WellnessCheckInUseCaseResponse;
import com.project.neurohelp.usecases.wellness.resources.CrisisResourcesUseCaseResponse;

import java.util.List;

public class WellnessConverter {

    public static WellnessCheckInUseCaseRequest toCheckInRequest(WellnessCheckInRequestPayload payload) {
        return WellnessCheckInUseCaseRequestBuilder.builder()
                .sessionId(payload.sessionId())
                .moodScore(payload.moodScore())
                .stressScore(payload.stressScore())
                .sleepHours(payload.sleepHours())
                .energyScore(payload.energyScore())
                .primaryFeeling(payload.primaryFeeling())
                .notes(payload.notes())
                .locale(payload.locale())
                .country(payload.country())
                .build();
    }

    public static WellnessCheckInResponsePayload toCheckInPayload(WellnessCheckInUseCaseResponse response) {
        List<WellnessCheckInResponsePayload.CrisisResourcePayload> crisisResources = response.crisisResources() == null
                ? List.of()
                : response.crisisResources().stream()
                .map(resource -> WellnessCheckInResponsePayloadCrisisResourcePayloadBuilder.builder()
                        .name(resource.name())
                        .contact(resource.contact())
                        .availability(resource.availability())
                        .notes(resource.notes())
                        .build())
                .toList();

        return WellnessCheckInResponsePayloadBuilder.builder()
                .sessionId(response.sessionId())
                .riskLevel(response.riskLevel())
                .summary(response.summary())
                .recommendedActions(response.recommendedActions() == null ? List.of() : response.recommendedActions())
                .groundingExercises(response.groundingExercises() == null ? List.of() : response.groundingExercises())
                .crisisResources(crisisResources)
                .followUpPrompt(response.followUpPrompt())
                .build();
    }

    public static CrisisResourcesResponsePayload toCrisisResourcesPayload(CrisisResourcesUseCaseResponse response) {
        List<CrisisResourcesResponsePayload.CrisisResourcePayload> resources = response.resources() == null
                ? List.of()
                : response.resources().stream()
                .map(resource -> CrisisResourcesResponsePayloadCrisisResourcePayloadBuilder.builder()
                        .name(resource.name())
                        .contact(resource.contact())
                        .availability(resource.availability())
                        .notes(resource.notes())
                        .build())
                .toList();

        return CrisisResourcesResponsePayloadBuilder.builder()
                .country(response.country())
                .locale(response.locale())
                .title(response.title())
                .message(response.message())
                .resources(resources)
                .immediateSteps(response.immediateSteps() == null ? List.of() : response.immediateSteps())
                .build();
    }
}

