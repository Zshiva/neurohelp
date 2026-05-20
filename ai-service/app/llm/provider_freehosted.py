import os
from app.llm.base import LlmClient
from openai import OpenAI
from dotenv import load_dotenv

# Load variables from the .env file
load_dotenv()

class FreeHostedLlmProvider(LlmClient):
    """
    Connects to the LLM defined in the .env file using the OpenAI SDK.
    Now configured to point to Gemini (using their OpenAI-compatible endpoint).
    """
    
    def __init__(self):
        # We read configuration from the .env file
        api_key = os.getenv("LLM_API_KEY", "dummy-key-replace-me")
        base_url = os.getenv("LLM_BASE_URL", "https://generativelanguage.googleapis.com/v1beta/openai/")
        self.model_name = os.getenv("LLM_MODEL_NAME", "gemini-1.5-flash")
        
        self.client = OpenAI(
            api_key=api_key,
            base_url=base_url
        )

    def generate(self, prompt: str) -> str:
        try:
            response = self.client.chat.completions.create(
                model=self.model_name,
                messages=[
                    {"role": "system", "content": "You are a helpful and safe AI assistant for the NeuroHelp platform."},
                    {"role": "user", "content": prompt}
                ],
                max_tokens=500,
                temperature=0.7
            )
            return response.choices[0].message.content
        except Exception as e:
            # Beginner-friendly error handling - return the error safely
            return f"[Error connecting to LLM]: {str(e)}"