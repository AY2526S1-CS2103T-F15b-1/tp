package insurabook.model;

import insurabook.model.client.Client;
import javafx.collections.ObservableList;

/**
 * An unmodifiable view of an insurabook
 */
public interface ReadOnlyInsuraBook {
    /**
     * Returns an unmodifiable view of the client list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getClientList();
}
