package model.expression;

import model.container.IDictionary;
import model.value.Value;
import model.container.IHeap;
import model.type.Type;

public class ValueExpression implements Expression {
    Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeap heap) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) {
        return value.getType();
    }
}
