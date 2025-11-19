package model.expression;

import exceptions.MochaDictionaryException;
import exceptions.MochaException;
import exceptions.MochaExpEvalException;
import model.container.IDictionary;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

public class ArithmeticExpression implements Expression {
    Expression left;
    Expression right;
    ArithmeticOperator operator;

    public ArithmeticExpression(Expression left, Expression right, ArithmeticOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaException {
        Value lValue = left.eval(symTable);
        Value rValue = right.eval(symTable);
        if (!lValue.getType().equals(new IntType()))
            throw new MochaExpEvalException("Left operand is not an integer.");
        if (!rValue.getType().equals(new IntType()))
            throw new MochaExpEvalException("Right operand is not an integer.");
        IntValue i1 = (IntValue) lValue;
        IntValue i2 = (IntValue) rValue;
        int n1 = i1.getValue();
        int n2 = i2.getValue();
        return switch (operator) {
            case ADD -> new IntValue(n1 + n2);
            case SUBTRACT -> new IntValue(n1 - n2);
            case MULTIPLY -> new IntValue(n1 * n2);
            case DIVIDE -> {
                if (n2 == 0) throw new MochaExpEvalException("Cannot divide by zero");
                yield new IntValue(n1 / n2);
            }
            default -> throw new MochaExpEvalException("Invalid arithmetic operator: " + operator);
        };
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

}
