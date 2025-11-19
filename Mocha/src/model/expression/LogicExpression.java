package model.expression;

import exceptions.MochaDictionaryException;
import exceptions.MochaException;
import exceptions.MochaExpEvalException;
import model.container.IDictionary;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

public class LogicExpression implements Expression {
    Expression left;
    Expression right;
    LogicOperator operator;

    public LogicExpression(Expression left, Expression right, LogicOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaException {
        Value lValue = left.eval(symTable);
        Value rValue = right.eval(symTable);
        if (!lValue.getType().equals(new BoolType())) {
            throw new MochaExpEvalException("Left operand is not a boolean.");
        }
        if (!rValue.getType().equals(new BoolType())) {
            throw new MochaExpEvalException("Right operand is not a boolean.");
        }
        BoolValue b1 = (BoolValue) lValue;
        BoolValue b2 = (BoolValue) rValue;
        boolean n1 = b1.getValue();
        boolean n2 = b2.getValue();
        return switch (operator) {
            case AND -> new BoolValue(n1 && n2);
            case OR -> new BoolValue(n1 || n2);
            default -> throw new MochaExpEvalException("Invalid logical operator: " + operator);
        };
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
