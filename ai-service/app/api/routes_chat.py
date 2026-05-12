from fastapi import APIRouter
from app.schemas.chat import ChatRequest, ChatResponse
from app.services.chat_service import ChatService

router = APIRouter()
chat_service = ChatService()

@router.post("/v1/chat", response_model=ChatResponse)
def chat_endpoint(request: ChatRequest):
    """
    Main Chat Endpoint.
    Routes the request to the chat_service pipeline.
    """
    response = chat_service.chat(request)
    return response
