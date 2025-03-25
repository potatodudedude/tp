package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Popup> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private VBox resultBox;

    @FXML
    private TextArea resultDisplay;

    private Popup popup;

    public ResultDisplay() {
        super(FXML);
        this.popup = new Popup();
        popup.setAutoHide(true);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        while (!popup.getContent().isEmpty()) {
            popup.getContent().remove(0);
        }
        popup.getContent().add(resultDisplay);
    }

    /**
     * Shows the result of the command.
     */
    public void show(Stage stage) {
        logger.fine("Showing result display panel...");
        popup.show(stage);
    }
}
