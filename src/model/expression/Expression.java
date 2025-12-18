package model.expression;

import exceptions.MochaException;
import model.container.IDictionary;
import model.value.Value;
import model.container.IHeap;
import model.type.Type;

public interface Expression {
    Value eval(IDictionary<String, Value> symTable, IHeap heap) throws MochaException;

    Type typeCheck(IDictionary<String, Type> typeEnv) throws MochaException;
}
