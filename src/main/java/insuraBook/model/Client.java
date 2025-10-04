package insuraBook.model;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private static int idSetter = 1;

    private final int clientId;
    private final String clientName;
    private final List<Policy> policies;

    public Client(String clientName) {
        this.clientName = clientName;
        this.clientId = idSetter;
        idSetter++;
        this.policies = new ArrayList<>();
    }

    public String getClientName() {
        return this.clientName;
    }

    public int getClientId() {
        return this.clientId;
    }

    public Policy getPolicy(int policy_id) {
        return null; // dummy
    }

    public boolean containsWord(String word) {
        return this.clientName.contains(word);
    }

    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    public void deletePolicy(Policy policy) {
        this.policies.remove(policy); // dummy
    }

    public String viewPolicies() {
        return ""; // dummy
    }

}
