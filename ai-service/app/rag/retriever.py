from app.rag.embeddings import Embedder
from app.rag.vector_store import FaissVectorStore

class RAGRetriever:
    def __init__(self, vector_store: FaissVectorStore):
        self.store = vector_store
        self.embedder = Embedder()

    def retrieve(self, query: str, top_k: int = 3) -> list[dict]:
        query_vec = self.embedder.embed_text(query)
        results = self.store.search(query_vec, top_k)
        return results
