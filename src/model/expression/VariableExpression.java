package model.expression;

import model.value.Value;
import model.container.IDictionary;
import exceptions.MochaExpEvalException;

public class VariableExpression implements Expression {
    String key;

    public VariableExpression(String key) {
        this.key = key;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaExpEvalException {
        return symTable.get(key);
    }

    @Override
    public String toString() {
        return key;
    }
}
