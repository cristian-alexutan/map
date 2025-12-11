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
        Statement ex5 = HardcodedPrograms.getStatement(4);
        Statement ex6 = HardcodedPrograms.getStatement(5);
        Statement ex7 = HardcodedPrograms.getStatement(6);
        Statement ex8 = HardcodedPrograms.getStatement(7);
        Statement ex9 = HardcodedPrograms.getStatement(8);

        Controller controller1 = new Interpreter().createController(ex1, "log1.txt");
        Controller controller2 = new Interpreter().createController(ex2, "log2.txt");
        Controller controller3 = new Interpreter().createController(ex3, "log3.txt");
        Controller controller4 = new Interpreter().createController(ex4, "log4.txt");
        Controller controller5 = new Interpreter().createController(ex5, "log5.txt");
        Controller controller6 = new Interpreter().createController(ex6, "log6.txt");
        Controller controller7 = new Interpreter().createController(ex7, "log7.txt");
        Controller controller8 = new Interpreter().createController(ex8, "log8.txt");
        Controller controller9 = new Interpreter().createController(ex9, "log9.txt");

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), controller6));
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), controller7));
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), controller8));
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), controller9));
        menu.show();
    }
}
