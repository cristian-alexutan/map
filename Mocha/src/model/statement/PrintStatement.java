package model.statement;

import exceptions.MochaException;
import model.container.IList;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;
import model.container.IDictionary;
import model.type.Type;

public class PrintStatement implements Statement {
    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IList<Value> out = state.getOut();
        out.add(expression.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}
