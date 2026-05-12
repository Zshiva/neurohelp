from fastapi import APIRouter

router = APIRouter()

@router.get("/health")
def health_check():
    """
    Health check endpoint: Returns {"status": "ok"}.
    The Java Spring Boot app will use this to verify the Python service is up.
    """
    return {"status": "ok"}
