package mango.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Visual component containing a speaker image and a text bubble.
 *
 * <p>Uses FXML to load its structure and styles. Provides factory methods for user
 * and bot dialog styles.</p>
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        assert text != null : "Dialog text must be non-null";
        assert img != null : "Dialog image must be non-null";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        assert dialog.getText() != null : "Label text must be set";
        assert displayPicture.getImage() != null : "ImageView must be set";
    }

    /**
     * Returns a dialog box styled for the user (image on the right).
     *
     * @param text The message text.
     * @param img The user's avatar image.
     * @return The configured {@code DialogBox}.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a dialog box styled for MangoBot (image on the left).
     *
     * @param text The bot's message text.
     * @param img The bot's avatar image.
     * @return The configured {@code DialogBox}.
     */
    public static DialogBox getMangoBotDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /** Flips the dialog so the image is on the left and the text on the right. */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }
}
