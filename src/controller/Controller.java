package controller;

import repository.IRepository;
import model.programstate.ProgramState;
import model.container.IStack;
import model.statement.Statement;
import exceptions.MochaException;

public class Controller {
    private IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    public ProgramState oneStep() throws MochaException {
        ProgramState programState = this.repository.getProgramState();
        IStack<Statement> exeStack = repository.getProgramState().getExeStack();
        if(exeStack.isEmpty()) throw new MochaException("ExeStack is empty");
        Statement currentStatement = exeStack.pop();
        return currentStatement.execute(programState);
    }

    public void allSteps() throws MochaException {
        ProgramState programState = this.repository.getProgramState();
        if(displayFlag) System.out.println(programState);
        IStack<Statement> exeStack = programState.getExeStack();
        while(!exeStack.isEmpty()) {
            oneStep();
            if(displayFlag) System.out.println(programState);
        }
    }
}
