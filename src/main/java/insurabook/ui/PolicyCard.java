package insurabook.ui;

import java.util.Comparator;
import java.util.List;

import insurabook.model.claims.Claim;
import insurabook.model.claims.InsuraDate;
import insurabook.model.policies.Policy;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PolicyCard extends UiPart<Region> {

    private static final String FXML = "PolicyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Policy policy;

    @FXML
    private HBox cardPane;
    @FXML
    private Label pName;
    @FXML
    private Label id;
    @FXML
    private Label pId;
    @FXML
    private Label pDate;
    @FXML
    private FlowPane claims;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PolicyCard(Policy policy, int displayedIndex) {
        super(FXML);
        this.policy = policy;
        id.setText(displayedIndex + ". ");
        pName.setText(policy.getPolicyType().getPtName().toString());
        pId.setText("Policy Id: " + policy.getPolicyId().toString());

        InsuraDate expiryDate = policy.getExpiryDate();
        pDate.setText(
                expiryDate != null
                        ? "Expiration Date: " + expiryDate.toUiString()
                        : "");

        List<Claim> claimList = policy.getClaims();
        claimList.stream()
                .sorted(Comparator.comparing(claim -> claim.getClaimId().toString()))
                .forEach(claim -> claims.getChildren().add(new Label("Claim ID: " + claim.getClaimId().toString())));
    }
}
