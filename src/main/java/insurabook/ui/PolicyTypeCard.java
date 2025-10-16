package insurabook.ui;

import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypePremium;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PolicyTypeCard extends UiPart<Region> {

    private static final String FXML = "PolicyTypeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final PolicyType policyType;

    @FXML
    private HBox cardPane;
    @FXML
    private Label ptName;
    @FXML
    private Label id;
    @FXML
    private Label ptId;
    @FXML
    private Label ptDescription;
    @FXML
    private Label ptPremium;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PolicyTypeCard(PolicyType policyType, int displayedIndex) {
        super(FXML);
        this.policyType = policyType;
        id.setText(displayedIndex + ". ");
        ptName.setText(policyType.getPtName().toString());
        ptId.setText("Policy Type Id: " + policyType.getPtId().toString());

        PolicyTypeDescription ptDescValue = policyType.getPtDescription();
        ptDescription.setText(
                !ptDescValue.isEmpty
                        ? ptDescValue.toString()
                        : "");

        PolicyTypePremium ptPremValue = policyType.getPtPremium();
        ptPremium.setText(
                !ptPremValue.isEmpty
                        ? "Policy Type Premium: " + policyType.getPtPremium()
                        : "");
    }
}
