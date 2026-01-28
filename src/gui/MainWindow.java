package gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import controller.Controller;
import model.programstate.ProgramState;
import model.value.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class MainWindow {
    private Controller controller;

    private TextField programStatesCount;

    private TableView<Map.Entry<Integer, Value>> heapTableView;
    private ObservableList<Map.Entry<Integer, Value>> heapObservableList;

    private ListView<Value> outListView;
    private ObservableList<Value> outObservableList;

    private ListView<String> fileTableListView;
    private ObservableList<String> fileTableObservableList;

    private TableView<Map.Entry<String, Value>> symTableView;
    private ObservableList<Map.Entry<String, Value>> symTableObservableList;

    private ListView<Integer> programStatesListView;
    private ObservableList<Integer> programStatesObservableList;

    private ListView<String> exeStackListView;
    private ObservableList<String> exeStackObservableList;

    public MainWindow(Controller controller) {
        this.controller = controller;

        programStatesCount = new TextField();
        programStatesCount.setEditable(false);

        heapTableView = new TableView<>();
        heapObservableList = observableArrayList();
        heapTableView.setItems(heapObservableList);
        TableColumn<Map.Entry<Integer, Value>, Integer> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getKey())
        );
        TableColumn<Map.Entry<Integer, Value>, Value> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getValue())
        );
        heapTableView.getColumns().setAll(addressCol, valueCol);

        outListView = new ListView<>();
        outObservableList = observableArrayList();
        outListView.setItems(outObservableList);

        fileTableListView = new ListView<>();
        fileTableObservableList = observableArrayList();
        fileTableListView.setItems(fileTableObservableList);

        symTableView = new TableView<>();
        symTableObservableList = observableArrayList();
        symTableView.setItems(symTableObservableList);
        TableColumn<Map.Entry<String, Value>, String> varNameCol = new TableColumn<>("Variable Name");
        varNameCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getKey())
        );
        TableColumn<Map.Entry<String, Value>, Value> varValueCol = new TableColumn<>("Value");
        varValueCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getValue())
        );
        symTableView.getColumns().setAll(varNameCol, varValueCol);

        programStatesListView = new ListView<>();
        programStatesObservableList = observableArrayList();
        programStatesListView.setItems(programStatesObservableList);
        programStatesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldId, newId) -> {
            updateSelectedProgram();
        });

        exeStackListView = new ListView<>();
        exeStackObservableList = FXCollections.observableArrayList();
        exeStackListView.setItems(exeStackObservableList);
    }

    private void updateSelectedProgram() {
        int idx = programStatesListView.getSelectionModel().getSelectedIndex();
        if(idx < 0) {
            symTableObservableList.clear();
            exeStackObservableList.clear();
            return;
        }
        List<ProgramState> programStates = controller.getProgramStates();
        List<ProgramState> active = programStates.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
        ProgramState state = active.get(idx);
        symTableObservableList.setAll(state.getSymTable().getContent().entrySet());
        exeStackObservableList.setAll(state.getExeStack().getContent().stream().map(Object::toString).collect(Collectors.toList()));
    }

    private void update() {
        List<ProgramState> programStates = controller.getProgramStates();
        List<ProgramState> active = programStates.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
        programStatesCount.setText("Number of program states: " + active.size());
        ProgramState state = programStates.getFirst();
        heapObservableList.setAll(state.getHeap().getContent().entrySet());
        outObservableList.setAll(state.getOut().getContent());
        fileTableObservableList.setAll(state.getFileTable().getContent().keySet());
        programStatesObservableList.setAll(active.stream().map(ProgramState::getId).collect(Collectors.toList()));
        if(programStatesListView.getSelectionModel().getSelectedItem() != null) {
            updateSelectedProgram();
        }
    }

    private void oneStepButtonEvent() {
        try {
            controller.oneStepForAllProgramsWithGC();
            update();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred during execution.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Mocha Interpreter");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button oneStepButton = new Button("One Step");
        oneStepButton.setOnAction(e -> oneStepButtonEvent());

        HBox topBar = new HBox(programStatesCount, spacer, oneStepButton);
        programStatesCount.setPrefWidth(300);

        VBox leftPane = new VBox(new Label("Program States"), programStatesListView);
        leftPane.setPrefWidth(200);
        VBox.setVgrow(programStatesListView, Priority.ALWAYS);

        VBox stackPane = new VBox(new Label("Execution Stack"), exeStackListView);
        VBox.setVgrow(exeStackListView, Priority.ALWAYS);

        VBox symPane = new VBox(new Label("Symbol Table"), symTableView);
        symPane.setPrefWidth(350);
        VBox.setVgrow(symTableView, Priority.ALWAYS);

        VBox heapBox = new VBox(new Label("Heap"), heapTableView);
        VBox outBox = new VBox(new Label("Out"), outListView);
        VBox fileBox = new VBox(new Label("File Table"), fileTableListView);

        HBox bottomRow = new HBox(heapBox, outBox, fileBox);
        HBox.setHgrow(heapBox, Priority.ALWAYS);
        HBox.setHgrow(outBox, Priority.ALWAYS);
        HBox.setHgrow(fileBox, Priority.ALWAYS);

        heapTableView.setPrefHeight(200);
        outListView.setPrefHeight(200);
        fileTableListView.setPrefHeight(200);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topBar);
        borderPane.setLeft(leftPane);
        borderPane.setCenter(stackPane);
        borderPane.setRight(symPane);
        borderPane.setBottom(bottomRow);

        borderPane.setStyle(
                """
                -fx-background-color: #c9c9ff;
                -fx-focus-color: #107aab;
                """
        );

        Scene scene = new Scene(borderPane, 1200, 750);
        stage.setScene(scene);
        stage.show();

        update();
    }
}
