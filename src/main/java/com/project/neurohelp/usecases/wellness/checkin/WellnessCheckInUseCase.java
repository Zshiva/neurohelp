package com.project.neurohelp.usecases.wellness.checkin;

import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.usecase.UseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WellnessCheckInUseCase implements UseCase<WellnessCheckInUseCaseRequest, WellnessCheckInUseCaseResponse> {

    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 10;
    private static final double MIN_SLEEP_HOURS = 0.0;
    private static final double MAX_SLEEP_HOURS = 24.0;

    @Override
    public Optional<WellnessCheckInUseCaseResponse> execute(WellnessCheckInUseCaseRequest request) {
        validate(request);

        String riskLevel = determineRiskLevel(request);
        String summary = buildSummary(request, riskLevel);
        List<String> recommendedActions = recommendedActions(riskLevel);
        List<String> groundingExercises = groundingExercises(riskLevel);
        List<WellnessCheckInUseCaseResponse.CrisisResource> crisisResources = crisisResources(riskLevel, normalizeCountry(request.country()));
        String followUpPrompt = followUpPrompt(riskLevel);

        return Optional.of(new WellnessCheckInUseCaseResponse(
                request.sessionId(),
                riskLevel,
                summary,
                recommendedActions,
                groundingExercises,
                crisisResources,
                followUpPrompt
        ));
    }

    private void validate(WellnessCheckInUseCaseRequest request) {
        if (request == null) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.WELLNESS_CHECK_IN_INVALID);
        }
        if (request.sessionId() == null || request.sessionId().isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.WELLNESS_SESSION_ID_REQUIRED);
        }
        if (isOutOfRange(request.moodScore()) || isOutOfRange(request.stressScore()) || isOutOfRange(request.energyScore())) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.WELLNESS_CHECK_IN_INVALID);
        }
        if (request.sleepHours() == null || request.sleepHours() < MIN_SLEEP_HOURS || request.sleepHours() > MAX_SLEEP_HOURS) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.WELLNESS_CHECK_IN_INVALID);
        }
    }

    private boolean isOutOfRange(Integer score) {
        return score == null || score < MIN_SCORE || score > MAX_SCORE;
    }

    private String determineRiskLevel(WellnessCheckInUseCaseRequest request) {
        int moodScore = request.moodScore();
        int stressScore = request.stressScore();
        int energyScore = request.energyScore();
        double sleepHours = request.sleepHours();

        if (moodScore <= 2 || stressScore >= 9 || sleepHours < 4.0 || energyScore <= 2) {
            return "CRISIS";
        }
        if (moodScore <= 4 || stressScore >= 7 || sleepHours < 6.0 || energyScore <= 4) {
            return "HIGH";
        }
        if (moodScore <= 6 || stressScore >= 5 || sleepHours < 7.0 || energyScore <= 6) {
            return "MODERATE";
        }
        return "LOW";
    }

    private String buildSummary(WellnessCheckInUseCaseRequest request, String riskLevel) {
        String feeling = isBlank(request.primaryFeeling()) ? "your current state" : request.primaryFeeling().trim();
        String notes = isBlank(request.notes()) ? "" : " Notes: " + request.notes().trim();

        return switch (riskLevel) {
            case "CRISIS" -> "Your check-in suggests you may need immediate support. Please focus on safety, stay with someone trusted if possible, and use the crisis resources below.";
            case "HIGH" -> "You seem to be having a very heavy day around " + feeling + ". Slow down, reduce pressure, and connect with support soon." + notes;
            case "MODERATE" -> "Your wellness check-in shows some strain around " + feeling + ". A short reset and a small support step may help right now." + notes;
            default -> "Your check-in looks relatively steady. Keep reinforcing the routines that support " + feeling + "." + notes;
        };
    }

    private List<String> recommendedActions(String riskLevel) {
        return switch (riskLevel) {
            case "CRISIS" -> List.of(
                    "Move toward immediate human support and do not stay alone if you can avoid it.",
                    "Use the crisis resources below or ask someone you trust to call for help with you.",
                    "Put away anything that could be used for self-harm and focus only on the next safe minute."
            );
            case "HIGH" -> List.of(
                    "Pause demanding tasks and create a quiet, low-stimulation environment.",
                    "Send a message or call one trusted person and let them know you need support.",
                    "Eat something light, drink water, and take a 10-minute recovery break."
            );
            case "MODERATE" -> List.of(
                    "Take a 4-4-6 breathing break for two minutes.",
                    "Go for a short walk or stretch to reset your body.",
                    "Plan one manageable task instead of trying to fix everything at once."
            );
            default -> List.of(
                    "Keep your current rhythm and protect the habits that are working.",
                    "Add one pleasant activity today, such as a short walk, music, or journaling.",
                    "Check in with yourself again later to notice any small changes early."
            );
        };
    }

    private List<String> groundingExercises(String riskLevel) {
        return switch (riskLevel) {
            case "CRISIS" -> List.of(
                    "Name 5 things you can see and 4 things you can touch right now.",
                    "Hold a cold glass of water or cool cloth to help bring your attention back to the present.",
                    "Slowly breathe in for 4 seconds and out for 6 seconds until you feel a little steadier."
            );
            case "HIGH" -> List.of(
                    "Try the 5-4-3-2-1 grounding scan for one full minute.",
                    "Put both feet on the floor and notice the support beneath you.",
                    "Relax your shoulders and unclench your jaw while counting six slow exhalations."
            );
            case "MODERATE" -> List.of(
                    "Use square breathing: inhale 4, hold 4, exhale 4, hold 4.",
                    "Describe the room around you in as much detail as possible.",
                    "Notice one thing you are grateful for in the current moment."
            );
            default -> List.of(
                    "Take three slow breaths and notice how your body feels.",
                    "Spend one minute paying attention to sounds around you.",
                    "Write one small win from today to reinforce a stable pattern."
            );
        };
    }

    private List<WellnessCheckInUseCaseResponse.CrisisResource> crisisResources(String riskLevel, String country) {
        if (!"CRISIS".equals(riskLevel) && !"HIGH".equals(riskLevel)) {
            return List.of();
        }

        if ("NP".equals(country) || country.contains("NEPAL")) {
            return List.of(
                    new WellnessCheckInUseCaseResponse.CrisisResource(
                            "National Suicide Prevention Hotline",
                            "1166",
                            "24/7",
                            "A Nepal-specific support line for urgent emotional distress and suicide prevention."
                    ),
                    new WellnessCheckInUseCaseResponse.CrisisResource(
                            "Nearest Emergency Department",
                            "Visit the nearest hospital emergency unit",
                            "24/7",
                            "If you feel unsafe, go with someone you trust or ask them to take you there."
                    ),
                    new WellnessCheckInUseCaseResponse.CrisisResource(
                            "Trusted Person",
                            "Call or message someone close to you",
                            "Right now",
                            "Ask them to stay with you or keep checking in until you feel safer."
                    )
            );
        }

        return List.of(
                new WellnessCheckInUseCaseResponse.CrisisResource(
                        "Local Emergency Services",
                        "Call your local emergency number",
                        "24/7",
                        "Use your country's urgent response service if you are in immediate danger."
                ),
                new WellnessCheckInUseCaseResponse.CrisisResource(
                        "Nearest Emergency Department",
                        "Go to the closest hospital emergency unit",
                        "24/7",
                        "Ask someone to accompany you if leaving alone feels unsafe."
                ),
                new WellnessCheckInUseCaseResponse.CrisisResource(
                        "Trusted Person",
                        "Contact a family member, friend, or neighbor",
                        "Right now",
                        "Stay connected until you have immediate support around you."
                )
        );
    }

    private String followUpPrompt(String riskLevel) {
        return switch (riskLevel) {
            case "CRISIS" -> "If you can, reply with one word that describes what you need most right now: safety, company, or calm.";
            case "HIGH" -> "Reply again after your first support step so we can adjust the plan together.";
            case "MODERATE" -> "Check back in later today with a quick score update to see if the plan helped.";
            default -> "Come back later and continue building the routines that keep your wellness steady.";
        };
    }

    private String normalizeCountry(String country) {
        return isBlank(country) ? "NP" : country.trim().toUpperCase();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

