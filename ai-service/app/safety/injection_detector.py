class InjectionDetector:
    def __init__(self):
        self.injection_signatures = [
            "ignore previous instructions",
            "you are now a",
            "system prompt",
            "bypass safety",
            "forget your rules"
        ]

    def is_injection(self, text: str) -> bool:
        text_lower = text.lower()
        return any(sig in text_lower for sig in self.injection_signatures)
