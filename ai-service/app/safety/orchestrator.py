from app.safety.crisis_detector import CrisisDetector
from app.safety.injection_detector import InjectionDetector
from app.safety.output_checker import OutputChecker
from app.safety.policy import CRISIS_RESPONSE

class SafetyOrchestrator:
    def __init__(self):
        self.crisis_detector = CrisisDetector()
        self.injection_detector = InjectionDetector()
        self.output_checker = OutputChecker()

    def check_input(self, text: str) -> dict:
        """Runs input stage checks and returns early-exit actions if needed."""
        if self.crisis_detector.is_crisis(text):
            return {"action": "CRISIS", "message": CRISIS_RESPONSE, "flags": ["crisis_detected"]}
        if self.injection_detector.is_injection(text):
            return {"action": "REFUSE", "message": "I cannot fulfill that request.", "flags": ["prompt_injection_attempt"]}
        return {"action": "OK"}

    def check_output(self, text: str) -> list[str]:
        """Validates LLM output before sending to user."""
        return self.output_checker.check_output(text)
