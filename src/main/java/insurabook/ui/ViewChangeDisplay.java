package insurabook.ui;

import static insurabook.logic.commands.ViewCommand.CLIENT_VIEW;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * An UI component that displays the current view (Client or Policy).
 */
public class ViewChangeDisplay extends UiPart<Region> {

    private static final String FXML = "ViewChangeDisplay.fxml";

    @FXML
    private TextArea viewChangeDisplay;

    /**
     * Creates a {@code ViewChangeDisplay} with the default view set to "Client View".
     */
    public ViewChangeDisplay() {
        super(FXML);
        viewChangeDisplay.setText("Client View");
    }

    /**
     * Updates the display to show the current view.
     * @param view The current view, either "client" or "policy".
     */
    public void setViewForUser(String view) {
        if (view.equals(CLIENT_VIEW)) {
            viewChangeDisplay.setText("Client View");
        } else {
            viewChangeDisplay.setText("Policy View");
        }
    }

}
