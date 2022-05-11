package com.example.toylanguageinterpretorgui;

import Controller.Controller;
import Domain.Statements.IStatement;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GuiController {
    @FXML
    private ListView<String> programsView;
    private ProgramsMenu programsMenu;

    public void initializeProgramSelectionStage() {
        this.programsMenu = new ProgramsMenu();
        List<String> programStatements = new ArrayList<>();
        List<IStatement> statementsList = programsMenu.getStatementsList();
        for (IStatement statement : statementsList) programStatements.add(statement.toString());
        ObservableList<String> observableProgramStatements = FXCollections.observableArrayList(programStatements);
        programsView.setItems(observableProgramStatements);
    }

    @FXML
    public void executeProgramButtonHandler() {
        int index = programsView.getSelectionModel().getSelectedIndex();
        if (index < 0) return;

        Controller chosenController = this.programsMenu.getControllerByIndex(index);
        Repository chosenRepository = chosenController.getProgramRepo();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("program-state-view.fxml"));
            Scene programStateScene = new Scene(fxmlLoader.load());
            Stage programStateStage = new Stage();
            programStateStage.setTitle("Program State Run");
            programStateStage.setScene(programStateScene);

            ProgramStateViewController programStateView = fxmlLoader.getController();
            programStateView.setProgramRepo(chosenRepository);
            programStateView.setProgramController(chosenController);
            programStateView.changeProgramStateView();
            programStateView.initializeTableViewsColumns();

            programStateStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.setTitle("Error!");
            alert.show();
        }
    }
}
