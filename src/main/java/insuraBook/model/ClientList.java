package insuraBook.model;

import java.util.ArrayList;
import java.util.List;

import insuraBook.exceptions.ClientMissingError;

/**
 * Class representing InsuraBook's storage of clients
 */
public class ClientList {

    private final List<Client> clientList;

    /**
     * Default constructor
     */
    public ClientList() {
        this.clientList = new ArrayList<>();
    }

    /**
     * Function to add client
     * @param client to add to clientList
     */
    public void addClient(Client client) {
        this.clientList.add(client);
    }

    /**
     * Function to delete client by name
     * @param name of client to delete, may not be unique
     * @throws ClientMissingError if there are no clients with or containing the name
     */
    public void deleteClient(String name) throws ClientMissingError {
        List<Client> matchingList = new ArrayList<>();
        for (int i = 0; i < this.clientList.size(); i++) {
            Client client = this.clientList.get(i);
            if (client.containsWord(name)) {
                matchingList.add(client);
            }
        }
        if (matchingList.isEmpty()) {
            throw new ClientMissingError("name");
        }
        if (matchingList.size() == 1) {
            this.clientList.remove(matchingList.get(0));
            // ui display success
        } else {
            // ui display matchingList
        }
    }

    /**
     * Function to delete client by id
     * @param id of client to delete, will be unique
     * @throws ClientMissingError if there are no clients with the same id
     */
    public void deleteClient(int id) throws ClientMissingError {
        boolean found = false;
        for (int i = 0; i < this.clientList.size(); i++) {
            Client client = this.clientList.get(i);
            if (id == client.getClientId()) {
                this.clientList.remove(i);
                found = true;
            }
        }
        if (!found) {
            throw new ClientMissingError("id");
        } else {
            // ui display success
        }
    }

}
