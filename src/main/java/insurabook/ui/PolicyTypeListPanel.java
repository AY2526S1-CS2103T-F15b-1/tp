package insurabook.ui;

import insurabook.commons.core.LogsCenter;
import insurabook.model.policytype.PolicyType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class PolicyTypeListPanel extends UiPart<Region> {
    private static final String FXML = "PolicyTypeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PolicyTypeListPanel.class);

    @FXML
    private ListView<PolicyType> policyTypeListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PolicyTypeListPanel(ObservableList<PolicyType> policyTypeList) {
        super(FXML);
        policyTypeListView.setItems(policyTypeList);
        policyTypeListView.setCellFactory(listView -> new PolicyListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PolicyListViewCell extends ListCell<PolicyType> {
        @Override
        protected void updateItem(PolicyType policyType, boolean empty) {
            super.updateItem(policyType, empty);

            if (empty || policyType == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PolicyTypeCard(policyType, getIndex() + 1).getRoot());
            }
        }
    }

}
