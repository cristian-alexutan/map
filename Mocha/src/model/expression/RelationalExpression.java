package model.expression;

import exceptions.MochaDictionaryException;
import exceptions.MochaException;
import exceptions.MochaExpEvalException;
import model.container.IDictionary;
import model.type.IntType;
import model.value.BoolValue;
import model.value.Value;

public class RelationalExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final RelationalOperator operator;

    public RelationalExpression(Expression left, Expression right, RelationalOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws MochaException {
        Value leftVal = left.eval(symTable);
        Value rightVal = right.eval(symTable);

        if (!leftVal.getType().equals(new IntType()) || !rightVal.getType().equals(new IntType())) {
            throw new RuntimeException("Relational expressions require integer operands.");
        }

        int leftInt = ((model.value.IntValue) leftVal).getValue();
        int rightInt = ((model.value.IntValue) rightVal).getValue();
        boolean result = switch (operator) {
            case LESS -> leftInt < rightInt;
            case LESS_EQUAL -> leftInt <= rightInt;
            case EQUAL -> leftInt == rightInt;
            case NOT_EQUAL -> leftInt != rightInt;
            case GREATER -> leftInt > rightInt;
            case GREATER_EQUAL -> leftInt >= rightInt;
            default -> throw new MochaExpEvalException("Invalid relational operator: " + operator);
        };

        return new BoolValue(result);
    }
}
