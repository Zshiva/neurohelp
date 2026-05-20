from abc import ABC, abstractmethod

class LlmClient(ABC):
    """
    Abstract base class for all LLM providers.
    This ensures that any AI provider we plug in (OpenAI, HuggingFace, Mock)
    will always have exactly the same method signatures.
    """
    
    @abstractmethod
    def generate(self, prompt: str) -> str:
        """
        Takes a prompt and returns the AI-generated text.
        """
        pass