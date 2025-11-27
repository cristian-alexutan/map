package interpreter;

import controller.Controller;
import exceptions.MochaException;
import view.command.ExitCommand;
import view.command.RunExampleCommand;
import model.container.*;
import model.programstate.ProgramState;
import model.statement.Statement;
import model.value.Value;
import repository.IRepository;
import repository.Repository;
import utils.HardcodedPrograms;
import view.textmenu.TextMenu;

import java.io.BufferedReader;

public class Interpreter {
    private Controller createController(Statement ex, String logFilePath) throws MochaException {
        IStack<Statement> exeStack = new MochaStack<>();
        IDictionary<String, Value> symTable = new MochaDictionary<>();
        IList<Value> out = new MochaList<>();
        IDictionary<String, BufferedReader> fileTable = new MochaDictionary<>();
        exeStack.push(ex);
        IHeap heap = new MochaHeap();
        ProgramState programState = new ProgramState(exeStack, symTable, out, fileTable, heap);
        IRepository repository = new Repository(programState, logFilePath);
        return new Controller(repository, true);
    }

    static void main(String[] args) throws MochaException {
        Statement ex1 = HardcodedPrograms.getStatement(0);
        Statement ex2 = HardcodedPrograms.getStatement(1);
        Statement ex3 = HardcodedPrograms.getStatement(2);
        Statement ex4 = HardcodedPrograms.getStatement(3);

        Controller controller1 = new Interpreter().createController(ex1, "log1.txt");
        Controller controller2 = new Interpreter().createController(ex2, "log2.txt");
        Controller controller3 = new Interpreter().createController(ex3, "log3.txt");
        Controller controller4 = new Interpreter().createController(ex4, "log4.txt");

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        menu.show();
    }
}
