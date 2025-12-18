package model.statement;

import exceptions.MochaException;
import model.container.IStack;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;
import model.container.IDictionary;
import model.type.Type;

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
        Value result = condition.eval(state.getSymTable(), state.getHeap());
        if (result.getType().equals(new BoolType())) {
            boolean cond = ((BoolValue) result).getValue();
            if (cond) stack.push(thenStatement);
            else stack.push(elseStatement);
            return null;
        } else {
            throw new MochaException("Condition is not a boolean.");
        }
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ") then(" + thenStatement.toString() + ") else(" + elseStatement.toString() + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        Type condType = condition.typeCheck(typeEnv);
        if (condType.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnv.deepCopy());
            elseStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MochaException("The condition of IF does not have the type bool.");
        }
    }
}
