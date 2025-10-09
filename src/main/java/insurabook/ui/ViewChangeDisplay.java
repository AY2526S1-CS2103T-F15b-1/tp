package insurabook.ui;

import static insurabook.logic.commands.ViewCommand.CLIENT_VIEW;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ViewChangeDisplay extends UiPart<Region> {

    private static final String FXML = "ViewChangeDisplay.fxml";

    @FXML
    private TextArea viewChangeDisplay;

    public ViewChangeDisplay() {
        super(FXML);
        viewChangeDisplay.setText("Client View");
    }

    public void setViewForUser(String view) {
        if (view.equals(CLIENT_VIEW)) {
            viewChangeDisplay.setText("Client View");
        } else {
            viewChangeDisplay.setText("Policy View");
        }
    }

}
