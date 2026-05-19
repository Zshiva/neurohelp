class CrisisDetector:
    def __init__(self):
        self.trigger_words = ["suicide", "kill myself", "end my life", "want to die", "hopeless", "harm myself"]

    def is_crisis(self, text: str) -> bool:
        text_lower = text.lower()
        return any(word in text_lower for word in self.trigger_words)
