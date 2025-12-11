package model.programstate;

import model.container.IDictionary;
import model.container.IList;
import model.container.IStack;
import model.statement.Statement;
import model.value.Value;
import model.container.IHeap;
import exceptions.MochaException;

import java.io.BufferedReader;

public class ProgramState {
    private IStack<Statement> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;
    private IDictionary<String, BufferedReader> fileTable;
    private IHeap heap;
    private static int lastId = 0;
    private final int id;

    public ProgramState(IStack<Statement> exeStack, IDictionary<String, Value> symTable, IList<Value> out, IDictionary<String, BufferedReader> fileTable, IHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = setId();
    }

    private static synchronized int setId() {
        lastId++;
        return lastId;
    }

    public int getId() {
        return this.id;
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

    public IHeap getHeap() {
        return heap;
    }

    public void setHeap(IHeap heap) {
        this.heap = heap;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    };

    @Override
    public String toString() {
        return  "Id: " + id + "\n" +
                "ExeStack:\n" + exeStack.toString() +
                "SymTable:\n" + symTable.toString() +
                "Out:\n" + out.toString() +
                "FileTable:\n" + fileTable.keyString() +
                "Heap:\n" + heap.toString();
    }

    public ProgramState deepCopy() {
        return new ProgramState(exeStack.deepCopy(), symTable.deepCopy(), out.deepCopy(), fileTable.deepCopy(), heap.deepCopy());
    }

    public ProgramState oneStep() throws MochaException{
        if (exeStack.isEmpty()) throw new MochaException("ExeStack is empty");
        Statement currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }
}
