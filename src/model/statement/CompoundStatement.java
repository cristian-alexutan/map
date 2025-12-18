package model.statement;

import model.container.IStack;
import model.programstate.ProgramState;
import model.type.Type;
import model.container.IDictionary;
import exceptions.MochaException;

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
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
}
