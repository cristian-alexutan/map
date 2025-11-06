package model.expression;

import exceptions.MochaExpEvalException;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;
import model.container.IDictionary;

public class LogicExpression implements Expression {
    Expression left;
    Expression right;
    String operator;

    public LogicExpression(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaExpEvalException {
        Value lValue = left.eval(symTable);
        Value rValue = right.eval(symTable);
        if(!lValue.getType().equals(new BoolType()))
            throw new MochaExpEvalException("Left operand is not a boolean.");
        if(!rValue.getType().equals(new BoolType()))
            throw new MochaExpEvalException("Right operand is not a boolean.");
        BoolValue b1 = (BoolValue) lValue;
        BoolValue b2 = (BoolValue) rValue;
        boolean n1 = b1.getValue();
        boolean n2 = b2.getValue();
        if(operator.equals("and")) return new BoolValue(n1 && n2);
        if(operator.equals("or")) return new BoolValue(n1 || n2);
        return null;
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
