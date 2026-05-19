from pydantic import BaseModel
from typing import List, Optional

class ChatRequest(BaseModel):
    session_id: str
    user_message: str
    locale: str = "en"
    country: str = "NP"
    mode: str = "default"

class Citation(BaseModel):
    source_id: str
    title: str
    url: Optional[str] = None
    chunk_id: str

class ChatResponse(BaseModel):
    assistant_message: str
    policy_action: str = "ANSWER"  # "ANSWER" | "REFUSE" | "CRISIS"
    risk_flags: List[str] = []
    citations: List[Citation] = []
