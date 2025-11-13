package interpreter;

import controller.*;
import exceptions.*;
import model.command.*;
import model.container.*;
import model.expression.*;
import model.programstate.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import repository.*;
import utils.*;
import view.*;
import utils.*;

import java.io.BufferedReader;
import java.io.IOException;

public class Interpreter {
    public static void main(String[] args) throws MochaException, IOException {
        HardcodedPrograms hardcodedPrograms = new HardcodedPrograms();

        Statement ex1 = hardcodedPrograms.getStatement(0);
        IStack<Statement> exeStack1 = new MochaStack<>();
        IDictionary<String, Value> symTable1 = new MochaDictionary<>();
        IList<Value> out1 = new MochaList<>();
        IDictionary<String, BufferedReader> fileTable1 = new MochaDictionary<>();
        exeStack1.push(ex1);
        ProgramState programState1 = new ProgramState(exeStack1, symTable1, out1, fileTable1);
        IRepository repository1 = new Repository(programState1, "log1.txt");
        Controller controller1 = new Controller(repository1, true);

        Statement ex2 = hardcodedPrograms.getStatement(1);
        IStack<Statement> exeStack2 = new MochaStack<>();
        IDictionary<String, Value> symTable2 = new MochaDictionary<>();
        IList<Value> out2 = new MochaList<>();
        IDictionary<String, BufferedReader> fileTable2 = new MochaDictionary<>();
        exeStack2.push(ex2);
        ProgramState programState2 = new ProgramState(exeStack2, symTable2, out2, fileTable2);
        IRepository repository2 = new Repository(programState2, "log2.txt");
        Controller controller2 = new Controller(repository2, true);

        Statement ex3 = hardcodedPrograms.getStatement(2);
        IStack<Statement> exeStack3 = new MochaStack<>();
        IDictionary<String, Value> symTable3 = new MochaDictionary<>();
        IList<Value> out3 = new MochaList<>();
        IDictionary<String, BufferedReader> fileTable3 = new MochaDictionary<>();
        exeStack3.push(ex3);
        ProgramState programState3 = new ProgramState(exeStack3, symTable3, out3, fileTable3);
        IRepository repository3 = new Repository(programState3, "log3.txt");
        Controller controller3 = new Controller(repository3, true);

        Statement ex4 = hardcodedPrograms.getStatement(3);
        IStack<Statement> exeStack4 = new MochaStack<>();
        IDictionary<String, Value> symTable4 = new MochaDictionary<>();
        IList<Value> out4 = new MochaList<>();
        IDictionary<String, BufferedReader> fileTable4 = new MochaDictionary<>();
        exeStack4.push(ex4);
        ProgramState programState4 = new ProgramState(exeStack4, symTable4, out4, fileTable4);
        IRepository repository4 = new Repository(programState4, "log4.txt");
        Controller controller4 = new Controller(repository4, true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        menu.show();
    }
}
