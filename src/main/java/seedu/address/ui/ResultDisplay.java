package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;

/**
 * A ui for the result popup that is displayed at the top of the application.
 */
public class ResultDisplay extends UiPart<Popup> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private VBox resultBox;

    @FXML
    private TextArea resultDisplay;

    private Popup popup;

    /**
     * Constructor for the result display
     */
    public ResultDisplay() {
        super(FXML);
        this.popup = new Popup();
        popup.setAutoHide(true);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        resultBox.getChildren().clear();
        resultBox.getChildren().add(resultDisplay);
        popup.getContent().clear();
        popup.getContent().add(resultBox);
    }

    /**
     * Shows the result of the command.
     */
    public void show(Stage stage, boolean isHelp) {
        logger.fine("Showing result display panel...");
        popup.show(stage);
        popup.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            popup.hide();
        });
    }
}
