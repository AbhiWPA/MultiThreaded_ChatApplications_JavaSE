package lk.epic.chatApp;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        new Thread(new Client()).start();
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
