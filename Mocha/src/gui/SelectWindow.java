package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import model.container.*;
import model.programstate.ProgramState;
import model.type.Type;
import model.value.Value;
import repository.IRepository;
import repository.Repository;
import utils.HardcodedPrograms;
import javafx.scene.Scene;
import model.statement.Statement;
import gui.MainWindow;

import java.io.BufferedReader;
import java.util.ArrayList;
import javafx.scene.control.ButtonType;

import java.util.List;

public class SelectWindow extends Application {
    private ListView<String> listView;
    private List<Statement> statements;

    public static void main(String[] args) {
        launch(args);
    }

    public void handleRunProgramButton() {
        int idx = listView.getSelectionModel().getSelectedIndex();
        if(idx < 0 || idx >= statements.size())  {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a valid program.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Statement selectedStatement = statements.get(idx);
        IDictionary<String, Type> typeEnv = new MochaDictionary<>();
        try {
            selectedStatement.typeCheck(typeEnv);
            IStack<Statement> exeStack = new MochaStack<>();
            IDictionary<String, Value> symTable = new MochaDictionary<>();
            IList<Value> out = new MochaList<>();
            IDictionary<String, BufferedReader> fileTable = new MochaDictionary<>();
            exeStack.push(selectedStatement);
            IHeap heap = new MochaHeap();
            ProgramState programState = new ProgramState(exeStack, symTable, out, fileTable, heap);
            IRepository repository = new Repository(programState, "log" + (idx + 1) + ".txt");
            Controller controller = new Controller(repository, true);

            MainWindow mainWindow = new MainWindow(controller);
            mainWindow.show();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Type checking failed.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Program");

        String [] statementStrings = new String[10];
        statements = new ArrayList<>();
        int maxLength = 0;
        for(int i = 0; i < 10; i++) {
            Statement current = HardcodedPrograms.getStatement(i);
            // System.out.println(current.toString());
            statements.add(current);
            statementStrings[i] = current.toString();
            if(statementStrings[i].length() > maxLength) {
                maxLength = statementStrings[i].length();
            }
        }

        ObservableList<String> items = FXCollections.observableArrayList(statementStrings);
        listView = new ListView<>();
        listView.setItems(items);

        Button runProgramButton = new Button("Run Program");
        runProgramButton.setOnAction(e -> handleRunProgramButton());

        VBox vbox = new VBox(listView, runProgramButton);
        Scene scene = new Scene(vbox, maxLength * 5, items.size() * 26 + 40);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
