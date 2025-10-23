package model.statement;

import model.programstate.ProgramState;
import model.container.IStack;

public class CompoundStatement implements Statement {
    private Statement first;
    private Statement second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<Statement> exeStack = state.getExeStack();
        exeStack.push(second);
        exeStack.push(first);
        state.setExeStack(exeStack);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
