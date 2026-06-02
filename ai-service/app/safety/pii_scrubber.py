import re
import logging

logger = logging.getLogger(__name__)

class PIIScrubber:
    """
    Detects and anonymizes Personally Identifiable Information (PII) from text.
    Currently uses Regex expressions for low latency and zero-dependency setup.
    
    NOTE: For production mental health apps, it is highly recommended to 
    upgrade this to use Microsoft Presidio (presidio-analyzer and presidio-anonymizer) 
    which uses NLP to detect names, locations, and context-aware PII.
    """
    def __init__(self):
        # Basic Regex patterns for common PII
        self.patterns = {
            "EMAIL": r'[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+',
            # Matches formats like (123) 456-7890, 123-456-7890, 123.456.7890
            "PHONE": r'\b\+?1?\s*\(?-*\.*[0-9]{3}\)?[-.\s]*[0-9]{3}[-.\s]*[0-9]{4}\b',
            "SSN": r'\b\d{3}-\d{2}-\d{4}\b',
            "CREDIT_CARD": r'\b(?:\d[ -]*?){13,16}\b',
        }

    def anonymize(self, text: str) -> str:
        """
        Replaces detected PII within text with generic placeholders (e.g., [EMAIL]).
        """
        if not text:
            return text
            
        anonymized_text = text
        for pii_type, pattern in self.patterns.items():
            anonymized_text = re.sub(pattern, f"[{pii_type}]", anonymized_text)
            
        if anonymized_text != text:
            logger.debug("PII detected and scrubbed from input.")
            
        return anonymized_text
