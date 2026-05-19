import json
import os
from app.rag.embeddings import Embedder
from app.rag.vector_store import FaissVectorStore

def chunk_text(text: str, chunk_size: int = 500, overlap: int = 50) -> list[str]:
    """Basic character-level chunking."""
    chunks = []
    start = 0
    while start < len(text):
        end = start + chunk_size
        chunks.append(text[start:end])
        start = end - overlap
    return chunks

def build_knowledge_base() -> FaissVectorStore:
    """Ingests static resources into memory for RAG."""
    embedder = Embedder()
    store = FaissVectorStore()
    
    # Example documents
    docs = [
        {"id": "doc_nepal_guidelines", "text": "In Nepal, mental health resources operate largely through NGOs like TPO Nepal (Transcultural Psychosocial Organization) and government hospitals like TUTH (Tribhuvan University Teaching Hospital)."},
        {"id": "doc_anxiety_tips", "text": "General anxiety management includes grounding techniques such as the 5-4-3-2-1 method, deep breathing, and routine maintenance. Clinical diagnosis requires a certified psychiatrist."}
    ]
    
    for doc in docs:
        chunks = chunk_text(doc["text"])
        embeddings = embedder.embed_batch(chunks)
        source_ids = [f"{doc['id']}_{i}" for i in range(len(chunks))]
        store.add_chunks(chunks, source_ids, embeddings)
        
    return store
