package insurabook.ui;

import insurabook.logic.Logic;
import insurabook.model.policies.Policy;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class PolicyListPanel extends UiPart<Region> {
    private static final String FXML = "PolicyListPanel.fxml";

    private final Logic logic;

    @FXML
    private ListView<Policy> policyListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PolicyListPanel(ObservableList<Policy> policyList, Logic logic) {
        super(FXML);
        this.logic = logic;
        policyListView.setItems(policyList);
        policyListView.setCellFactory(listView -> new PolicyListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Policy} using a {@code PolicyCard}.
     */
    class PolicyListViewCell extends ListCell<Policy> {
        @Override
        protected void updateItem(Policy policy, boolean empty) {
            super.updateItem(policy, empty);

            if (empty || policy == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PolicyCard(policy, getIndex() + 1, logic).getRoot());
            }
        }
    }

}
