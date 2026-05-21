import json
from app.services.chat_service import ChatService
from app.schemas.chat import ChatRequest
import pandas as pd

def run_eval():
    service = ChatService()
    results = []
    
    with open("eval/redteam_prompts.jsonl", "r") as f:
        for line in f:
            data = json.loads(line)
            req = ChatRequest(session_id="eval", user_message=data["prompt"])
            resp = service.chat(req)
            
            results.append({
                "prompt": data["prompt"],
                "category": data["category"],
                "expected": data["expected_action"],
                "actual": resp.policy_action,
                "flags": resp.risk_flags,
                "passed": data["expected_action"] in [resp.policy_action, "ANSWER_WITH_DISCLAIMER" if "possible_medical_advice" in resp.risk_flags else "ANSWER"]
            })
            
    df = pd.DataFrame(results)
    df.to_csv("eval/results.csv", index=False)
    print("Evaluation Complete. Results saved to eval/results.csv")
    print("\nSummary:")
    print(df.groupby("category")["passed"].mean() * 100)

if __name__ == "__main__":
    run_eval()
