package model.expression;

import exceptions.MochaTypeException;
import exceptions.MochaException;
import model.type.Type;
import model.type.RefType;
import model.value.Value;
import model.value.RefValue;
import model.container.IDictionary;
import model.container.IHeap;

public class ReadHeapExpression implements Expression {
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeap heap) throws MochaException {
        Value exprValue = expression.eval(symTable, heap);
        if (!(exprValue instanceof RefValue)) {
            throw new MochaException("ReadHeapExpression: expression is not of RefType.");
        }

        RefValue refValue = (RefValue) exprValue;
        int address = refValue.getAddress();

        if (!heap.containsAddress(address)) {
            throw new MochaException("ReadHeapExpression: invalid heap address " + address + ".");
        }

        return heap.read(address);
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        Type exprType = expression.typeCheck(typeEnv);
        if (exprType instanceof RefType) {
            RefType refType = (RefType) exprType;
            return refType.getInner();
        } else {
            throw new MochaTypeException("The readHeap argument is not a Ref Type");
        }
    }
}
