package model.statement;

import exceptions.MochaException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;
import model.value.BoolValue;
import model.type.BoolType;
import model.container.IStack;

public class IfStatement implements Statement {
    Expression condition;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IStack<Statement> stack = state.getExeStack();
        Value result = condition.eval(state.getSymTable());
        if(result.getType().equals(new BoolType())) {
            boolean cond = ((BoolValue) result).getValue();
            if (cond) stack.push(thenStatement);
            else stack.push(elseStatement);
            state.setExeStack(stack);
        }
        else {
            throw new MochaException("Condition is not a boolean.");
        }
        return null;
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ") then(" + thenStatement.toString() + ") else(" + elseStatement.toString() + ")";
    }
}
