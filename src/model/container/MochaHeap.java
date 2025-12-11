package model.container;

import exceptions.MochaException;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MochaHeap implements IHeap {
    Map<Integer, Value> heap;
    int nextFree;

    public MochaHeap() {
        heap = new HashMap<>();
        nextFree = 1;
    }

    @Override
    public synchronized int allocate(Value value) {
        heap.put(nextFree, value);
        return nextFree++;
    }

    @Override
    public synchronized void deallocate(int address) throws MochaException {
        if (!heap.containsKey(address))
            throw new MochaException("Invalid heap address: " + address);
        heap.remove(address);
    }

    @Override
    public synchronized Value read(int address) throws MochaException {
        if (!heap.containsKey(address))
            throw new MochaException("Invalid heap address: " + address);
        return heap.get(address);
    }

    @Override
    public synchronized void write(int address, Value value) throws MochaException {
        if (!heap.containsKey(address))
            throw new MochaException("Invalid heap address: " + address);
        heap.put(address, value);
    }

    @Override
    public synchronized boolean containsAddress(int address) {
        return heap.containsKey(address);
    }

    @Override
    public synchronized Map<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public synchronized void setContent(Map<Integer, Value> newContent) {
        heap = newContent;
    }

    @Override
    public synchronized String toString() {
        StringBuilder ans = new StringBuilder();
        for (int address : heap.keySet())
            ans.append(address).append(" -> ").append(heap.get(address).toString()).append("\n");
        return ans.toString();
    }

    @Override
    public synchronized IHeap deepCopy() {
        MochaHeap newHeap = new MochaHeap();
        newHeap.nextFree = this.nextFree;
        for (int address : heap.keySet()) {
            newHeap.heap.put(address, heap.get(address));
        }
        return newHeap;
    }
}