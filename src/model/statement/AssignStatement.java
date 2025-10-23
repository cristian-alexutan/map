package model.statement;

import exceptions.MochaException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;
import model.type.Type;
import model.container.IDictionary;

public class AssignStatement implements Statement {
    String key;
    Expression expression;

    public AssignStatement(String key, Expression expression) {
        this.key = key;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        if (!state.getSymTable().hasKey(key))
            throw new MochaException("The variable " + key + " was not declared before.");
        Value val = expression.eval(state.getSymTable());
        Type typeId = (state.getSymTable().get(key)).getType();
        if (val.getType().equals(typeId)) state.getSymTable().insert(key, val);
        else throw new MochaException("Declared type of variable " + key + " and type of the assigned expression do not match.");
        IDictionary<String, Value> symTable = state.getSymTable();
        symTable.update(key, val);
        state.setSymTable(symTable);
        return state;
    }

    @Override
    public String toString() {
        return key + " = " + expression.toString();
    }
}
