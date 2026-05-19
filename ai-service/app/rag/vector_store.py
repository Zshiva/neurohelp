import numpy as np

class FaissVectorStore:
    """
    Beginner-Friendly FAISS wrapper for local, fast vector search.
    """
    def __init__(self, embedding_dim: int = 384):
        # We import here for safe failure if faiss isn't installed yet
        try:
            import faiss
            # IndexFlatL2 measures the Euclidean distance between vectors
            self.index = faiss.IndexFlatL2(embedding_dim)
        except ImportError:
            print("Please install faiss: pip install faiss-cpu")
            self.index = None
            
        self.chunks = []  # Keeps track of the actual text
        self.source_ids = [] # Keeps track of where the text came from

    def add_chunks(self, chunks: list[str], source_ids: list[str], embeddings: np.ndarray):
        """Adds text chunks and their embeddings into the FAISS index."""
        if self.index is None:
            return
            
        # Add vectors to the FAISS index
        self.index.add(embeddings.astype('float32'))
        
        # Store the text so we can retrieve it later
        self.chunks.extend(chunks)
        self.source_ids.extend(source_ids)

    def search(self, query_embedding: np.ndarray, top_k: int = 3) -> list[dict]:
        """Searches for the closest chunks to the query."""
        if self.index is None or self.index.ntotal == 0:
            return []

        # FAISS expects a 2D array, so we reshape the single query vector
        query_embedding_2d = query_embedding.reshape(1, -1).astype('float32')
        
        # Search returns distances and the indices of the closest vectors
        distances, indices = self.index.search(query_embedding_2d, top_k)
        
        results = []
        # Extract the text and source ID for each matching index
        for idx in indices[0]:
            if idx != -1 and idx < len(self.chunks):
                results.append({
                    "chunk": self.chunks[idx],
                    "source_id": self.source_ids[idx]
                })
                
        return results
