package model.container;

import exceptions.MochaException;
import exceptions.MochaListException;

import java.util.List;

public interface IList<T> {
    void add(T val);

    T popFront() throws MochaException;

    T front() throws MochaListException;

    boolean isEmpty();

    String toString();

    IList<T> deepCopy();

    List<T> getContent();
}
