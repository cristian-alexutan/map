package model.expression;

import exceptions.MochaDictionaryException;
import exceptions.MochaExpEvalException;
import model.value.Value;
import model.value.BoolValue;
import model.type.IntType;
import model.type.Type;
import model.container.IDictionary;

public class RelationalExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    public RelationalExpression(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaDictionaryException, MochaExpEvalException {
        Value leftVal = left.eval(symTable);
        Value rightVal = right.eval(symTable);

        if (!leftVal.getType().equals(new IntType()) || !rightVal.getType().equals(new IntType())) {
            throw new RuntimeException("Relational expressions require integer operands.");
        }

        int leftInt = ((model.value.IntValue) leftVal).getValue();
        int rightInt = ((model.value.IntValue) rightVal).getValue();
        boolean result;

        switch (operator) {
            case "<":
                result = leftInt < rightInt;
                break;
            case "<=":
                result = leftInt <= rightInt;
                break;
            case "==":
                result = leftInt == rightInt;
                break;
            case "!=":
                result = leftInt != rightInt;
                break;
            case ">":
                result = leftInt > rightInt;
                break;
            case ">=":
                result = leftInt >= rightInt;
                break;
            default:
                throw new RuntimeException("Invalid relational operator: " + operator);
        }

        return new BoolValue(result);
    }
}
