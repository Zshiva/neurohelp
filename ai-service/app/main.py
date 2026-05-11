import os
import sys

# Ensure the 'ai-service' root directory is in the Python path
# This allows 'python app/main.py' to resolve 'app.x' imports correctly
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from fastapi import FastAPI
import uvicorn

# Import our routers
from app.api.routes_health import router as health_router
from app.api.routes_chat import router as chat_router

# 1. Initialize the FastAPI app
app = FastAPI(
    title="NeuroHelp AI Service",
    description="Python AI Service implementing a strict RAG pipeline with safety checks.",
    version="0.1.0"
)

# 2. Include Routers
app.include_router(health_router, tags=["Health"])
app.include_router(chat_router, tags=["Chat"])

# 3. Entrypoint to run the server
if __name__ == "__main__":
    # Run dev server on port 8000 with auto-reload
    uvicorn.run("app.main:app", host="0.0.0.0", port=8000, reload=True)
