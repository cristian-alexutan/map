package model.statement;

import exceptions.MochaException;
import model.programstate.ProgramState;
import model.value.Value;
import model.container.IList;
import model.expression.Expression;

public class PrintStatement implements Statement {
    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IList<Value> out = state.getOut();
        out.add(expression.eval(state.getSymTable()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
