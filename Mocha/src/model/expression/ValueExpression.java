package model.expression;

import model.container.IDictionary;
import model.value.Value;

public class ValueExpression implements Expression {
    Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
