class OutputChecker:
    def __init__(self):
        self.forbidden_patterns = ["diagnosis", "take", "mg of", "dosage", "prescribe"]

    def check_output(self, text: str) -> list[str]:
        flags = []
        text_lower = text.lower()
        
        if any(pat in text_lower for pat in self.forbidden_patterns):
            flags.append("possible_medical_advice")
            
        if "hallucinated_phone_number" in text_lower:  # Simple heuristic for demo
            flags.append("unverified_resource")
            
        return flags
