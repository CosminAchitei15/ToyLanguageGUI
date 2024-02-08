package com.example.toylanguagegui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGUI extends Application {
    @Override
    public void start(Stage mainstage) throws IOException {
        FXMLLoader mainfxmlLoader = new FXMLLoader(MainGUI.class.getResource("runProgram.fxml"));
        Scene mainscene = new Scene(mainfxmlLoader.load(), 600, 600);
        mainstage.setTitle("Run Window");
        mainstage.setScene(mainscene);
        RunProgramController mainController = mainfxmlLoader.getController();
        mainstage.show();

        FXMLLoader selectfxmlLoader = new FXMLLoader(MainGUI.class.getResource("selectProgram.fxml"));
        Scene selectScene = new Scene(selectfxmlLoader.load(), 600, 600);
        Stage selectStage = new Stage();
        selectStage.setTitle("Select Window");
        selectStage.setScene(selectScene);
        SelectProgramController selectController = selectfxmlLoader.getController();
        selectController.setMainController(mainController);
        selectStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}