package model.statement;

import model.programstate.ProgramState;
import model.container.IStack;

public class CompoundStatement implements Statement {
    private final Statement first;
    private final Statement second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<Statement> exeStack = state.getExeStack();
        exeStack.push(second);
        exeStack.push(first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
