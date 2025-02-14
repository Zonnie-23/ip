package nova;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import nova.ui.DialogBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Nova nova;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image novaImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Nova instance */
    public void setNova(Nova nova) {
        this.nova = nova;
        String response = this.nova.getInitialMessage();
        dialogContainer.getChildren().addAll(
                DialogBox.getNovaDialog(response, novaImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = nova.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNovaDialog(response, novaImage)
        );
        userInput.clear();
        if (!nova.isActive()) {
            closeAfterDelay();
        }
    }

    private void closeAfterDelay() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1)); // wait 3 seconds
        delay.setOnFinished(event -> {
            // Get the stage from any node in the scene
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            stage.close();
        });
        delay.play();
    }
}
