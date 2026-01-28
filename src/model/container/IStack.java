package model.container;

import exceptions.MochaException;

import java.util.Arrays;
import java.util.List;

public interface IStack<T> {
    void push(T val);

    T pop() throws MochaException;

    T top() throws MochaException;

    boolean isEmpty();

    String toString();

    IStack<T> deepCopy();

    List<T> getContent();
}
