package model.expression;

import exceptions.MochaDictionaryException;
import exceptions.MochaExpEvalException;
import model.value.Value;
import model.container.IDictionary;
import model.type.IntType;
import model.value.IntValue;

public class ArithmeticExpression implements Expression {
    Expression left;
    Expression right;
    char operator;

    public ArithmeticExpression(Expression left, Expression right, char operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaExpEvalException, MochaDictionaryException {
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
        switch (operator) {
            case '+': {
                return new IntValue(n1 + n2);
            }
            case '-': {
                return new IntValue(n1 - n2);
            }
            case '*': {
                return new IntValue(n1 * n2);
            }
            case '/': {
                if (n2 == 0) throw new MochaExpEvalException("Cannot divide by zero");
                return new IntValue(n1 / n2);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

}
