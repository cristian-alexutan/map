package model.container;

import exceptions.MochaException;
import model.value.Value;

import java.util.HashMap;

public interface IHeap {
    int allocate(Value value);
    void deallocate(int address) throws MochaException;
    Value read(int address) throws MochaException;
    void write(int address, Value value) throws MochaException;
    boolean containsAddress(int address);
    HashMap<Integer, Value> getHashMap();
    IHeap deepCopy();
}
