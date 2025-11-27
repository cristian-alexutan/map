package model.expression;

import exceptions.MochaDictionaryException;
import exceptions.MochaException;
import exceptions.MochaExpEvalException;
import model.container.IDictionary;
import model.value.Value;
import model.container.IHeap;

public interface Expression {
    Value eval(IDictionary<String, Value> symTable, IHeap heap) throws MochaException;
}
