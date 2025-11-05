package model.statement;

import exceptions.MochaException;
import model.programstate.ProgramState;
import model.type.Type;
import model.container.IDictionary;
import model.value.Value;

public class DeclarationStatement implements Statement {
    private String varName;
    private Type varType;

    public DeclarationStatement(String varName, Type varType) {
        this.varName = varName;
        this.varType = varType;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.hasKey(varName)) throw new MochaException("Variable " + varName + " is already declared.");
        symTable.insert(varName, varType.defaultValue());
        return state;
    }

    @Override
    public String toString() {
        return varType.toString() + " " + varName;
    }
}
