package mango.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mango.core.MangoBot;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private MangoBot mangoBot;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("MangoBot");

            this.mangoBot = new MangoBot("./data/mango.txt");
            fxmlLoader.<MainWindow>getController().setMangoBot(this.mangoBot);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
