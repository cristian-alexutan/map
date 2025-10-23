package model.container;

import exceptions.MochaException;

public interface IList<T> {
    void add(T val);
    T popFront() throws MochaException;
    T front();
    boolean isEmpty();
    String toString();
}
