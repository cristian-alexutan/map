package model.container;

import exceptions.MochaException;
import model.value.Value;

import java.util.Map;

public interface IHeap {
    int allocate(Value value);

    void deallocate(int address) throws MochaException;

    Value read(int address) throws MochaException;

    void write(int address, Value value) throws MochaException;

    boolean containsAddress(int address);

    Map<Integer, Value> getContent();

    void setContent(Map<Integer, Value> newContent);

    IHeap deepCopy();

    String toString();
}
