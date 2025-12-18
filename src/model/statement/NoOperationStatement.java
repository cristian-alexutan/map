package model.statement;

import model.programstate.ProgramState;
import model.type.Type;
import model.container.IDictionary;

public class NoOperationStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public String toString() {
        return "NOP";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) {
        return typeEnv;
    }
}
