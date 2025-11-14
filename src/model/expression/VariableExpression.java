package model.expression;

import exceptions.MochaDictionaryException;
import exceptions.MochaExpEvalException;
import model.container.IDictionary;
import model.value.Value;

public class VariableExpression implements Expression {
    String key;

    public VariableExpression(String key) {
        this.key = key;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaExpEvalException, MochaDictionaryException {
        if (!symTable.hasKey(key)) {
            throw new MochaExpEvalException("Variable not defined");
        }
        return symTable.get(key);
    }

    @Override
    public String toString() {
        return key;
    }
}
