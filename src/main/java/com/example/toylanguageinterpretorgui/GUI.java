package com.example.toylanguageinterpretorgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("selection-view.fxml"));
        Scene selectionScene = new Scene(fxmlLoader.load());
        stage.setTitle("Select Program");
        stage.setScene(selectionScene);
        GuiController program_controller = fxmlLoader.getController();
        program_controller.initializeProgramSelectionStage();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}