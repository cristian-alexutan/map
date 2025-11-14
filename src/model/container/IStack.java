package model.container;

import exceptions.MochaException;

public interface IStack<T> {
    void push(T val);

    T pop() throws MochaException;

    T top() throws MochaException;

    boolean isEmpty();

    String toString();

    IStack<T> deepCopy();
}
