package model.container;

import exceptions.MochaStackException;

import java.util.LinkedList;

public class MochaStack<T> implements IStack<T> {
    private LinkedList<T> list;

    public MochaStack() {
        list = new LinkedList<>();
    }

    @Override
    public void push(T val) {
        list.addFirst(val);
    }

    @Override
    public T pop() throws MochaStackException {
        if (list.isEmpty()) throw new MochaStackException("Stack is empty");
        return list.removeFirst();
    }

    @Override
    public T top() throws MochaStackException {
        if (list.isEmpty()) throw new MochaStackException("Stack is empty");
        return list.peekFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (T val : list) {
            ans.append(val.toString()).append("\n");
        }
        return ans.toString();
    }

    @Override
    public IStack<T> deepCopy() {
        MochaStack<T> newStack = new MochaStack<>();
        LinkedList<T> tempList = new LinkedList<>();
        for (T val : list) {
            tempList.addFirst(val);
        }
        for (T val : tempList) {
            newStack.push(val);
        }
        return newStack;
    }
}
