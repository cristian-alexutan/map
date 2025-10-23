package model.programstate;

import model.container.IStack;
import model.container.IList;
import model.container.IDictionary;
import model.statement.Statement;
import model.value.Value;

public class ProgramState {
    private IStack<Statement> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;

    ProgramState(IStack<Statement> exeStack, IDictionary<String, Value> symTable, IList<Value> out) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
    }

    public IStack<Statement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IStack<Statement> exeStack) {
        this.exeStack = exeStack;
    }

    public IDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(IDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public IList<Value> getOut() {
        return out;
    }

    public void setOut(IList<Value> out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "ExeStack:\n" + exeStack.toString() + "\nSymTable:\n" + symTable.toString() + "\nOut:\n" + out.toString();
    }
}
