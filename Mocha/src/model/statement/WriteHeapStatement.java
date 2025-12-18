package model.statement;

import exceptions.MochaException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.Type;
import model.type.RefType;
import model.value.Value;
import model.value.RefValue;
import model.container.IHeap;
import model.container.IDictionary;

public class WriteHeapStatement implements Statement {
    private final String varName;
    private final Expression expression;

    public WriteHeapStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        if (!symTable.hasKey(varName)) {
            throw new MochaException("WriteHeapStatement: variable " + varName + " is not defined.");
        }

        Value varValue = symTable.get(varName);
        if (!(varValue instanceof RefValue)) {
            throw new MochaException("WriteHeapStatement: variable " + varName + " is not of RefType.");
        }

        RefValue refValue = (RefValue) varValue;
        int address = refValue.getAddress();

        if (!heap.containsAddress(address)) {
            throw new MochaException("WriteHeapStatement: invalid heap address " + address + ".");
        }

        Value exprValue = expression.eval(symTable, heap);
        Type locationType = refValue.getLocationType();

        if (!exprValue.getType().equals(locationType)) {
            throw new MochaException("WriteHeapStatement: type mismatch. Expected " + locationType + ", got " + exprValue.getType() + ".");
        }

        heap.write(address, exprValue);
        return null;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression.toString() + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        Type varType = typeEnv.get(varName);
        if (!(varType instanceof RefType)) {
            throw new MochaException("WriteHeap Statement: variable " + varName + " isn't RefType.");
        }

        RefType refType = (RefType) varType;
        Type exprType = expression.typeCheck(typeEnv);

        if (!exprType.equals(refType.getInner())) {
            throw new MochaException("WriteHeapStatement: type mismatch. Expected " + refType.getInner() + ", got " + exprType + ".");
        }

        return typeEnv;
    }
}
