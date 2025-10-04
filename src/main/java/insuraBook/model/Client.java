package insuraBook.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing individual clients
 */
public class Client {

    private static int idSetter = 1;

    private final int clientId;
    private final String clientName;
    private final List<Policy> policies;

    /**
     * Constructor for client
     * @param clientName name of client, does not have to be unique
     */
    public Client(String clientName) {
        this.clientName = clientName;
        this.clientId = idSetter;
        idSetter++;
        this.policies = new ArrayList<>();
    }

    /**
     * Getter
     * @return String client name
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * Getter
     * @return int client id
     */
    public int getClientId() {
        return this.clientId;
    }

    /**
     * Function to get policy from client's records
     * @param policyId of policy to be found
     * @return Policy with matching policyId if found
     */
    public Policy getPolicy(int policyId) {
        return null; // dummy
    }

    /**
     * Function to check if client's name contains specified word
     * @param word to check with client's name
     * @return True if client's name contains word, else False
     */
    public boolean containsWord(String word) {
        return this.clientName.contains(word);
    }

    /**
     * Function to add policy to client's records
     * @param policy to add
     */
    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    /**
     * Function to delete policy from client's records
     * @param policy to delete
     */
    public void deletePolicy(Policy policy) {
        this.policies.remove(policy); // dummy
    }

    /**
     * Function to return String format of all currently stored policies
     * @return String of currently stored policies
     */
    public String viewPolicies() {
        return ""; // dummy
    }

}
