package model.programstate;

import model.container.IDictionary;
import model.container.IList;
import model.container.IStack;
import model.statement.Statement;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
    private IStack<Statement> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;
    private IDictionary<String, BufferedReader> fileTable;

    public ProgramState(IStack<Statement> exeStack, IDictionary<String, Value> symTable, IList<Value> out, IDictionary<String, BufferedReader> fileTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
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

    public IDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    @Override
    public String toString() {
        return "ExeStack:\n" + exeStack.toString() + "SymTable:\n" + symTable.toString() + "Out:\n" + out.toString() + "FileTable:\n" + fileTable.keyString();
    }

    public ProgramState deepCopy() {
        return new ProgramState(exeStack.deepCopy(), symTable.deepCopy(), out.deepCopy(), fileTable.deepCopy());
    }
}
