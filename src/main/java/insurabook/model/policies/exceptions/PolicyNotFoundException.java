package insurabook.model.policies.exceptions;

public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException() {
        super("Policy not found");
    }
}
