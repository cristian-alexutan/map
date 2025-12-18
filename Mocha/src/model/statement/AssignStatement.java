package model.statement;

import exceptions.MochaException;
import model.container.IDictionary;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.Type;
import model.value.Value;

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
        Value val = expression.eval(state.getSymTable(), state.getHeap());
        Type typeId = (state.getSymTable().get(key)).getType();
        if (val.getType().equals(typeId)) state.getSymTable().insert(key, val);
        else
            throw new MochaException("Declared type of variable " + key + " and type of the assigned expression do not match.");
        IDictionary<String, Value> symTable = state.getSymTable();
        symTable.update(key, val);
        return null;
    }

    @Override
    public String toString() {
        return key + " = " + expression.toString();
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        Type varType = typeEnv.get(key);
        Type expType = expression.typeCheck(typeEnv);
        if (varType.equals(expType)) {
            return typeEnv;
        } else {
            throw new MochaException("Assign Statement: right hand side and left hand side have different types");
        }
    }
}
