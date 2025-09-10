package mango.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mango.core.MangoBot;

/**
 * A GUI for MangoBot using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        assert stage != null : "Stage must be provided by JavaFX";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "FXML must load";
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("MangoBot");

            MangoBot mangoBot = new MangoBot("./data/mango.txt");
            assert mangoBot != null : "MangoBot must be created";
            fxmlLoader.<MainWindow>getController().setMangoBot(mangoBot);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
