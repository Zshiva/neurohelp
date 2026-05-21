from app.schemas.chat import ChatRequest, ChatResponse, Citation
from app.llm.base import LlmClient
from app.llm.provider_freehosted import FreeHostedLlmProvider
from app.safety.orchestrator import SafetyOrchestrator
from app.safety.policy import SYSTEM_PROMPT
from app.rag.ingest import build_knowledge_base
from app.rag.retriever import RAGRetriever

class ChatService:
    def __init__(self):
        self.llm_client: LlmClient = FreeHostedLlmProvider()
        self.safety = SafetyOrchestrator()
        
        # Build RAG in memory (Advanced feature loaded)
        self.kb_store = build_knowledge_base()
        self.retriever = RAGRetriever(self.kb_store)

    def chat(self, request: ChatRequest) -> ChatResponse:
        # 1. Input Normalization
        cleaned_message = request.user_message.strip()[:1000] # Limit size
        
        # 2. Safety Orchestration (Input Stage)
        input_check = self.safety.check_input(cleaned_message)
        if input_check["action"] != "OK":
            return ChatResponse(
                assistant_message=input_check["message"],
                policy_action=input_check["action"],
                risk_flags=input_check["flags"],
                citations=[]
            )
            
        # 3. RAG Retrieval 
        retrieved_items = self.retriever.retrieve(cleaned_message)
        
        # Format Context
        context_str = "\n".join([f"[{item['source_id']}] {item['chunk']}" for item in retrieved_items])
        citations = [Citation(source_id=item["source_id"].split("_")[0], title="Internal KB", chunk_id=item["source_id"]) for item in retrieved_items]
            
        # 4. Prompt Assembly
        system_instructions = SYSTEM_PROMPT.format(context=context_str)
        final_prompt = f"{system_instructions}\n\nUser: {cleaned_message}\nAssistant:"

        # 5. LLM Call
        assistant_reply = self.llm_client.generate(final_prompt)

        # 6. Safety Orchestration (Output Stage)
        output_flags = self.safety.check_output(assistant_reply)
        action = "ANSWER"
        if "possible_medical_advice" in output_flags:
            assistant_reply += "\n\n(Disclaimer: I am an AI, not a doctor. Please verify any medical actions with a professional.)"
            
        # 7. Return Result
        return ChatResponse(
            assistant_message=assistant_reply,
            policy_action=action,
            risk_flags=output_flags,
            citations=citations
        )
