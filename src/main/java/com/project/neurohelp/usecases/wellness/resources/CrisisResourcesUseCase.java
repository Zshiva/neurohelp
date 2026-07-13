package com.project.neurohelp.usecases.wellness.resources;

import com.project.neurohelp.platform.usecase.UseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrisisResourcesUseCase implements UseCase<CrisisResourcesUseCaseRequest, CrisisResourcesUseCaseResponse> {

    @Override
    public Optional<CrisisResourcesUseCaseResponse> execute(CrisisResourcesUseCaseRequest request) {
        String country = normalizeCountry(request == null ? null : request.country());
        String locale = normalizeLocale(request == null ? null : request.locale());

        boolean nepalFocused = "NP".equals(country) || country.contains("NEPAL");
        List<CrisisResourcesUseCaseResponse.CrisisResource> resources = nepalFocused ? nepalResources() : globalResources();
        List<String> immediateSteps = List.of(
                "Move to a safer, quieter place and reduce stimulation.",
                "Contact one trusted person and ask them to stay connected with you.",
                "Use the emergency contact or hotline above if you are in immediate danger."
        );

        return Optional.of(new CrisisResourcesUseCaseResponse(
                country,
                locale,
                nepalFocused ? "Nepal crisis support resources" : "Immediate crisis support resources",
                nepalFocused
                        ? "These resources are tailored for people seeking urgent support in Nepal."
                        : "These resources provide immediate safety support and emergency contact options.",
                resources,
                immediateSteps
        ));
    }

    private List<CrisisResourcesUseCaseResponse.CrisisResource> nepalResources() {
        return List.of(
                new CrisisResourcesUseCaseResponse.CrisisResource(
                        "National Suicide Prevention Hotline",
                        "1166",
                        "24/7",
                        "Use this line for urgent emotional distress, suicide risk, or when you need immediate support."
                ),
                new CrisisResourcesUseCaseResponse.CrisisResource(
                        "Nearest Emergency Department",
                        "Visit the nearest hospital emergency unit",
                        "24/7",
                        "If there is immediate danger, go to the nearest emergency department now."
                ),
                new CrisisResourcesUseCaseResponse.CrisisResource(
                        "Trusted Person",
                        "Call or message a trusted family member or friend",
                        "Right now",
                        "Ask them to stay with you or help you get to care."
                )
        );
    }

    private List<CrisisResourcesUseCaseResponse.CrisisResource> globalResources() {
        return List.of(
                new CrisisResourcesUseCaseResponse.CrisisResource(
                        "Local Emergency Services",
                        "Call your local emergency number",
                        "24/7",
                        "If you are in immediate danger, contact emergency services first."
                ),
                new CrisisResourcesUseCaseResponse.CrisisResource(
                        "Nearest Emergency Department",
                        "Go to the closest hospital emergency unit",
                        "24/7",
                        "Ask someone to accompany you if possible."
                ),
                new CrisisResourcesUseCaseResponse.CrisisResource(
                        "Trusted Person",
                        "Contact a family member, friend, or neighbor",
                        "Right now",
                        "Stay connected while you wait for professional help or calming support."
                )
        );
    }

    private String normalizeCountry(String country) {
        return country == null || country.isBlank() ? "NP" : country.trim().toUpperCase();
    }

    private String normalizeLocale(String locale) {
        return locale == null || locale.isBlank() ? "en" : locale.trim().toLowerCase();
    }
}

