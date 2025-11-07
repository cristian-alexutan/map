package model.container;

import java.util.ArrayList;
import exceptions.MochaListException;

public class MochaList<T> implements IList<T> {
    ArrayList<T> list;

    public MochaList() {
        list = new ArrayList<>();
    }

    @Override
    public void add(T val) {
        list.add(val);
    }

    @Override
    public T popFront() throws MochaListException {
        if(list.isEmpty()) throw new MochaListException("List is empty");
        return list.removeFirst();
    }

    @Override
    public T front() throws MochaListException {
        if(list.isEmpty()) throw new MochaListException("List is empty");
        return list.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
