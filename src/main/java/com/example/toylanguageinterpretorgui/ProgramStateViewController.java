package com.example.toylanguageinterpretorgui;

import Controller.Controller;
import Domain.ADTs.StackADT;
import Domain.ProgramState;
import Domain.Statements.IStatement;
import Domain.Values.IValue;
import Repository.Repository;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import Exception.InvalidKeyException;
import Exception.IsEmptyException;


public class ProgramStateViewController {
    Controller controller;
    Repository repository;

    public Button runOneStepButton;

    @FXML
    private TextField numberOfStates;

    @FXML
    private TableView<Map.Entry<Integer, String>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> heapValueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableView;

    @FXML
    private ListView<Integer> programStatesIDView;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symbolVariableNameColumn;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symbolValueColumn;

    @FXML
    private TableView<Map.Entry<String, IValue>> selectedSymbolTableView;

    @FXML
    private ListView<String> selectedExecutionStack;

    public void setProgramRepo(Repository repository) {
        this.repository = repository;
    }

    public void setProgramController(Controller controller) {
        this.controller = controller;
    }

    public void initializeTableViewsColumns() {
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        symbolVariableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symbolValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
    }

    public void changeProgramStateView() throws InvalidKeyException, IsEmptyException {
        updateStatesNumber();
        if (isProgramExecutionDone()) printProgramExecutionEndMessage();
        else {
            populateHeapTable();
            populateOutputTable();
            populateFileTable();
            populateProgramStatesID();
            populateSelectedSymbolTable();
            populateSelectedExecutionStack();
        }
    }

    private void updateStatesNumber() {
        String statesNumber = Integer.toString(repository.getProgramsList().size());
        numberOfStates.setText(statesNumber);
    }

    private void populateHeapTable() throws InvalidKeyException {
        Map<Integer, String> heap = repository.getProgramsList().get(0).getHeap().getHeapDisplayFormatContent();
        List<Map.Entry<Integer, String>> modifiedHeap = new ArrayList<>(heap.entrySet());
        heapTableView.setItems(FXCollections.observableList(modifiedHeap));
        heapTableView.refresh();
    }

    private void populateOutputTable() throws InvalidKeyException {
        List<String> outputValues = repository.getProgramsList().get(0).getOutput().toList();
        outputListView.setItems(FXCollections.observableList(outputValues));
        outputListView.refresh();
    }

    private void populateFileTable() throws InvalidKeyException {
        HashMap<String, BufferedReader> fileTable = repository.getProgramsList().get(0).getFileTable().getMapContent();
        List<String> files = new LinkedList<>(fileTable.keySet());
        fileTableView.setItems(FXCollections.observableList(files));
        fileTableView.refresh();
    }

    private void populateProgramStatesID() {
        List<ProgramState> programStates = repository.getProgramsList().toList();
        List<Integer> programStateIds = new LinkedList<>();
        for (ProgramState state : programStates) programStateIds.add(state.getId());
        programStatesIDView.setItems(FXCollections.observableList(programStateIds));
        programStatesIDView.refresh();
    }

    private void populateSelectedSymbolTable() {
        ProgramState programState = getSelectedProgramState();
        if (programState == null) return;
        Map<String, IValue> symbolTable;
        symbolTable = programState.getSymbolTable().getMapContent();
        List<Map.Entry<String, IValue>> symbolTableContent = new LinkedList<>(symbolTable.entrySet());
        selectedSymbolTableView.setItems(FXCollections.observableList(symbolTableContent));
        selectedSymbolTableView.refresh();
    }

    private void populateSelectedExecutionStack() throws IsEmptyException {
        ProgramState programState = getSelectedProgramState();
        List<String> executionStackContent = new LinkedList<>();
        if (programState == null) return;
        StackADT<IStatement> shallow_copy_stack;
        shallow_copy_stack = programState.getExecutionStack().shallowCopy();
        while (!shallow_copy_stack.isEmpty()) {
            executionStackContent.add(shallow_copy_stack.getTop().toString());
            shallow_copy_stack.pop();
        }
        selectedExecutionStack.setItems(FXCollections.observableList(executionStackContent));
        selectedExecutionStack.refresh();
    }

    private boolean isProgramExecutionDone() {
        return repository.getProgramsList().size() == 0;
    }

    private void printProgramExecutionEndMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Program is over!");
        alert.setTitle("Warning");
        alert.showAndWait();
    }

    @FXML
    public void clickedProgramStateHandler() throws InvalidKeyException, IsEmptyException {
        changeProgramStateView();
    }

    @FXML
    public void runOneStepButtonHandler() throws InvalidKeyException, IsEmptyException {
        if (isProgramExecutionDone()) return;

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<ProgramState> prgList = controller.removeCompletedPrograms(repository.getProgramsList().toList());
        prgList.forEach(prg -> {
            try {
                repository.logProgramStateExecution(prg);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.setTitle("Error!");
                alert.show();
            }
        });
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());
        try {
            List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            showError(e);
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            prgList.forEach(prg -> {
                try {
                    repository.logProgramStateExecution(prg);
                } catch (Exception e) {
                    showError(e);
                }
            });
            repository.setProgramsListFromList(prgList);
        } catch (InterruptedException e) {
            showError(e);
        }
        executor.shutdownNow();
        changeProgramStateView();
    }

    private void showError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.setTitle("Error!");
        alert.show();
    }


    private ProgramState getSelectedProgramState() {
        Integer selected_program_id = getSelectedProgramStateID();
        if (selected_program_id == null) return null;
        for (ProgramState state : repository.getProgramsList().toList()) {
            if (state.getId() == selected_program_id) {
                return state;
            }
        }
        return null;
    }

    private Integer getSelectedProgramStateID() {
        return programStatesIDView.getSelectionModel().getSelectedItem();
    }
}
