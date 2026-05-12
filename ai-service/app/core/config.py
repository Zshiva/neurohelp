from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    LLM_API_KEY: str = "dummy"
    LLM_BASE_URL: str = "https://generativelanguage.googleapis.com/v1beta/openai/"
    LLM_MODEL_NAME: str = "gemini-1.5-flash"
    
    class Config:
        env_file = ".env"

settings = Settings()