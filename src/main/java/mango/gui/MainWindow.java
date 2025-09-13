package mango.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mango.core.MangoBot;
import mango.ui.Messages;

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

    private MangoBot mangoBot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image mangoBotImage = new Image(this.getClass().getResourceAsStream("/images/MangoBot.png"));

    /**
     * Initializes UI bindings after FXML is loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the {@link MangoBot} instance used by the controller.
     *
     * @param mangoBot the bot backing this window.
     */
    public void setMangoBot(MangoBot mangoBot) {
        assert mangoBot != null : "Injected MangoBot must be non-null";
        this.mangoBot = mangoBot;
        dialogContainer.getChildren().add(
                DialogBox.getMangoBotDialog(Messages.welcome(), mangoBotImage)
        );

        if (mangoBot.getStartupErrorMessage() != null) {
            dialogContainer.getChildren().add(
                    DialogBox.getMangoBotDialog(mangoBot.getStartupErrorMessage(), mangoBotImage)
            );
        }
    }

    /**
     * Processes the current input submission.
     *
     * <p>Adds the user's message and MangoBot's reply to the dialog container, then clears
     * the input field. If the input is {@code "bye"} (case-insensitive), disables the
     * input controls and quits the application after a short delay.</p>
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = this.mangoBot.respond(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMangoBotDialog(response, mangoBotImage)
        );
        userInput.clear();

        if ("bye".equalsIgnoreCase(input.trim())) {
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition pause = new PauseTransition(Duration.millis(800));
            pause.setOnFinished(e -> Platform.exit());
            pause.play();
        }
    }
}
