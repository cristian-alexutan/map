package model.statement;

import exceptions.MochaException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.Type;
import model.type.BoolType;
import model.value.Value;
import model.value.BoolValue;
import model.container.IDictionary;
import model.container.IStack;

public class WhileStatement implements Statement {
    private Expression condition;
    private Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IStack<Statement> exeStack = state.getExeStack();

        Value condValue = condition.eval(symTable, state.getHeap());
        if (!condValue.getType().equals(new BoolType())) {
            throw new MochaException("While condition is not a boolean.");
        }

        boolean cond = ((BoolValue) condValue).getValue();
        if (cond) {
            exeStack.push(this);
            exeStack.push(body);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ") { " + body.toString() + " }";
    }
}
