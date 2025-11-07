package model.expression;

import exceptions.MochaDictionaryException;
import model.value.Value;
import model.container.IDictionary;
import exceptions.MochaExpEvalException;

public interface Expression {
    Value eval(IDictionary<String, Value> symTable) throws MochaExpEvalException, MochaDictionaryException;
}
