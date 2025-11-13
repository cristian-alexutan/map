package controller;

import exceptions.MochaException;
import repository.IRepository;
import model.programstate.ProgramState;
import model.container.IStack;
import model.statement.Statement;
import exceptions.MochaExecutionException;

import java.io.IOException;

public class Controller {
    private IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    public boolean getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public ProgramState oneStep() throws MochaException {
        ProgramState programState = this.repository.getProgramState();
        IStack<Statement> exeStack = repository.getProgramState().getExeStack();
        if (exeStack.isEmpty()) {
            throw new MochaExecutionException("ExeStack is empty");
        }
        Statement currentStatement = exeStack.pop();
        return currentStatement.execute(programState);
    }

    public void allSteps() throws MochaException, IOException {
        ProgramState programState = this.repository.getProgramState();
        if (displayFlag) {
            repository.logPrgStateExec();
        }
        IStack<Statement> exeStack = programState.getExeStack();
        while (!exeStack.isEmpty()) {
            oneStep();
            if (displayFlag) {
                repository.logPrgStateExec();
            }
        }
    }
}
