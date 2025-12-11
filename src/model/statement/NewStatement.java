package model.statement;

import exceptions.MochaException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.Type;
import model.type.RefType;
import model.value.Value;
import model.value.RefValue;
import model.container.IDictionary;
import model.container.IHeap;

public class NewStatement implements Statement{
    private final String varName;
    private final Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        if (!symTable.hasKey(varName)) {
            throw new MochaException("Variable " + varName + " is not defined.");
        }

        Value varValue = symTable.get(varName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new MochaException("Variable " + varName + " is not of RefType.");
        }

        RefType refType = (RefType) varValue.getType();
        Value exprValue = expression.eval(symTable, heap);

        if (!exprValue.getType().equals(refType.getInner())) {
            throw new MochaException("Type mismatch: variable " + varName + " and expression do not match.");
        }

        int newAddress = heap.allocate(exprValue);
        RefValue newRefValue = new RefValue(newAddress, refType.getInner());
        symTable.update(varName, newRefValue);

        return null;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression.toString() + ")";
    }
}
