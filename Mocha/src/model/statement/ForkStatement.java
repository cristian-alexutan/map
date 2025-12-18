package model.statement;

import exceptions.MochaException;
import model.programstate.ProgramState;
import model.type.Type;
import model.container.IDictionary;
import model.container.MochaDictionary;
import model.container.IStack;
import model.container.MochaStack;
import model.value.Value;

import java.util.Map;

public class ForkStatement implements Statement{
    private Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MochaException {
        IStack<Statement> newExeStack = new MochaStack<>();
        IDictionary<String, Value> newSymTable = new MochaDictionary<>();
        newExeStack.push(statement);

        IDictionary<String, Value> currentSymTable = state.getSymTable();
        for (Map.Entry<String, Value> entry : currentSymTable.getContent().entrySet()) {
            newSymTable.insert(entry.getKey(), entry.getValue().deepCopy());
        }

        return new ProgramState(newExeStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MochaException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
