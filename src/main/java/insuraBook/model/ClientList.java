package insuraBook.model;

import java.util.ArrayList;
import java.util.List;

import insuraBook.exceptions.clientMissingError;

public class ClientList {

    private final List<Client> clientList;

    public ClientList() {
        this.clientList = new ArrayList<>();
    }

    public void addClient(Client client) {
        this.clientList.add(client);
    }

    public void deleteClient(String name) throws clientMissingError {
        List<Client> matchingList = new ArrayList<>();
        for (int i = 0; i < this.clientList.size(); i++) {
            Client client = this.clientList.get(i);
            if (client.containsWord(name)) {
                matchingList.add(client);
            }
        }
        if (matchingList.isEmpty()) {
            throw new clientMissingError("name");
        }
        if (matchingList.size() == 1) {
            this.clientList.remove(matchingList.get(0));
            // ui display success
        } else {
            // ui display matchingList
        }
    }

    public void deleteClient(int id) throws clientMissingError {
        boolean found = false;
        for (int i = 0; i < this.clientList.size(); i++) {
            Client client = this.clientList.get(i);
            if (id == client.getClientId()) {
                this.clientList.remove(i);
                found = true;
            }
        }
        if (!found) {
            throw new clientMissingError("id");
        } else {
            // ui display success
        }
    }

}
