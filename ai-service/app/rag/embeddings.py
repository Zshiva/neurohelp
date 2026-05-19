import numpy as np

class Embedder:
    """
    Beginner-Friendly Embedder using SentenceTransformers.
    This converts text into arrays of numbers (vectors) locally and for free!
    """
    def __init__(self, model_name="all-MiniLM-L6-v2"):
        # We import here so the app doesn't crash if the library isn't installed yet
        try:
            from sentence_transformers import SentenceTransformer
            # all-MiniLM-L6-v2 is extremely fast, free, and lightweight
            self.model = SentenceTransformer(model_name)
        except ImportError:
            print("Please install sentence-transformers: pip install sentence-transformers")
            self.model = None

    def embed_text(self, text: str) -> np.ndarray:
        """Converts a single string into a vector."""
        if not self.model:
            # Fallback dummy vector if library isn't installed
            return np.random.rand(384).astype('float32')
        return self.model.encode(text)

    def embed_batch(self, texts: list[str]) -> np.ndarray:
        """Converts a list of strings into a matrix of vectors."""
        if not self.model:
            return np.random.rand(len(texts), 384).astype('float32')
        return self.model.encode(texts)
