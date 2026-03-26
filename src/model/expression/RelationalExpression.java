package model.expression;

import exceptions.MochaTypeException;
import exceptions.MochaException;
import exceptions.MochaExpEvalException;
import model.container.IDictionary;
import model.type.IntType;
import model.value.BoolValue;
import model.value.Value;
import model.container.IHeap;
import model.type.Type;

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
    public Value eval(IDictionary<String, Value> symTable, IHeap heap) throws MochaException {
        Value leftVal = left.eval(symTable, heap);
        Value rightVal = right.eval(symTable, heap);

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

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator.toString() + " " + right.toString() + ")";
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        Type leftType = left.typeCheck(typeEnv);
        Type rightType = right.typeCheck(typeEnv);

        if (!leftType.equals(new IntType())) {
            throw new MochaTypeException("Left operand is not an integer.");
        }
        if (!rightType.equals(new IntType())) {
            throw new MochaTypeException("Right operand is not an integer.");
        }

        return new model.type.BoolType();
    }
}
