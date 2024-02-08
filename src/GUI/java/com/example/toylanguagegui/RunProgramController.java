package com.example.toylanguagegui;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.Model.*;
import com.example.toylanguagegui.src.Model.Statement.*;
import com.example.toylanguagegui.src.utils.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunProgramController {
    private Controller controller;

    @FXML
    private TableView<heapTableViewClass> heapTableView;

    @FXML
    private TableColumn<heapTableViewClass, String> heapAddressColumn;

    @FXML
    private TableColumn<heapTableViewClass, String> heapValueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private ListView<Integer> prgStateListView;

    @FXML
    private TableView<symTableViewClass> symTableView;

    @FXML
    private TableColumn<symTableViewClass, String> symTableVariableColumn;

    @FXML
    private TableColumn<symTableViewClass, String> symTableValueColumn;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private TextField nrOfProgramStatesTextField;

    @FXML
    private Button oneStepButton;

    public void setController(Controller c){
        this.controller = c;
        populatePrgStateIDs();
    }

    private List<Integer> getPrgStateIds(List<PrgState> prgStateList){
        return prgStateList.stream().map(PrgState::getThreadid).collect(Collectors.toList());
    }

    private void populatePrgStateIDs(){
        List<PrgState> programStates = controller.getRepo().getRepo();
        prgStateListView.setItems(FXCollections.observableList(getPrgStateIds(programStates)));
        nrOfProgramStatesTextField.setText("" + programStates.size());
    }

    public void initialize(){
        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<heapTableViewClass, String>("address"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<heapTableViewClass, String>("value"));

        symTableVariableColumn.setCellValueFactory(new PropertyValueFactory<symTableViewClass, String>("name"));
        symTableValueColumn.setCellValueFactory(new PropertyValueFactory<symTableViewClass, String>("value"));

        prgStateListView.setOnMouseClicked(mouseEvent -> {changePrgState(getCurrentPrgState());});
        oneStepButton.setOnAction(actionEvent -> {
            try {
                oneStepGUI();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private PrgState getCurrentPrgState(){
        if(prgStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;
        int currid = prgStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getPrgStateByID(currid);
    }

    private void changePrgState(PrgState currentPrgState){
        if(currentPrgState == null)
            return;
        populateExeStack(currentPrgState);
        populateHeapTable(currentPrgState);
        populateSymTable(currentPrgState);
        populateFileTable(currentPrgState);
        populateOutput(currentPrgState);
    }

    private void oneStepGUI() throws InterruptedException {
        if(controller == null){
            Alert err = new Alert(Alert.AlertType.ERROR, "You did not select a program", ButtonType.OK);
            err.showAndWait();
            return;
        }
        if(getCurrentPrgState().getExeStack().isEmpty()){
            Alert err = new Alert(Alert.AlertType.ERROR, "Program is finished", ButtonType.OK);
            err.showAndWait();
            return;
        }
        controller.oneStepForGUI();
        changePrgState(getCurrentPrgState());
        populatePrgStateIDs();
    }

    private void populateOutput(PrgState currentPrgState) {
        MyIList<Value> output = currentPrgState.getOut();
        String outputString = output.toString();
        String[] outputStringList = outputString.split(" ");
        outputListView.setItems(FXCollections.observableArrayList(outputStringList));
    }

    private void populateFileTable(PrgState currentPrgState) {
        MyIDictionary<StringValue, BufferedReader> filetable = currentPrgState.getFileTable();
        String fileTableString = filetable.toString();
        fileListView.setItems(FXCollections.observableArrayList(fileTableString));
    }

    private void populateSymTable(PrgState currentPrgState) {
        this.symTableView.getItems().clear();
        MyIDictionary<String, Value> symTable = currentPrgState.getSymTable();
        Map<String, Value> symTableMap = symTable.getMap();
        symTableMap.forEach((name, value)->this.symTableView.getItems().add(new symTableViewClass(name, value)));
    }

    private void populateHeapTable(PrgState currentPrgState) {
        this.heapTableView.getItems().clear();
        MyIHeap<Value> heap = currentPrgState.getHeap();
        Map<Integer, Value> heapMap = heap.getHeap();
        heapMap.forEach((address, value)->this.heapTableView.getItems().add(new heapTableViewClass(address, value)));
    }

    private void populateExeStack(PrgState currentPrgState) {
        MyIStack<IStmt> exeStack = currentPrgState.getExeStack();
        List<String> exeStackList = new ArrayList<>();
        for(IStmt stmt : exeStack.reverse()){
            exeStackList.add(stmt.toString());
        }
        exeStackListView.setItems(FXCollections.observableList(exeStackList));
    }
}
