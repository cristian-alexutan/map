package interpreter;

import controller.Controller;
import exceptions.MochaException;
import model.type.Type;
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
import java.util.Hashtable;

public class Interpreter {
    private Controller createController(Statement ex, String logFilePath) throws MochaException {
        IDictionary<String, Type> typeEnv = new MochaDictionary<>();
        try {
            ex.typeCheck(typeEnv);
        } catch (MochaException e) {
            throw new MochaException("Type check error: " + e.getMessage() + " for the program: " + ex.toString());
        }
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

    static void main(String[] args) {
        Statement[] statements = new Statement[10];
        Controller[] controllers = new Controller[10];
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        for (int i = 0; i < 10; i++) {
            statements[i] = HardcodedPrograms.getStatement(i);
            try {
                controllers[i] = new Interpreter().createController(statements[i], "log" + (i + 1) + ".txt");
                menu.addCommand(new RunExampleCommand(Integer.toString(i + 1), statements[i].toString(), controllers[i]));
            } catch (MochaException e) {
                System.out.println("Could not add example " + (i + 1) + ": " + e.getMessage());
            }
        }
        menu.show();
    }
}
