package model.container;

import java.util.ArrayList;
import exceptions.MochaException;

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
    public T popFront() throws MochaException {
        if(list.isEmpty()) throw new MochaException("List is empty");
        return list.removeFirst();
    }

    @Override
    public T front() {
        if(list.isEmpty()) throw new RuntimeException("List is empty");
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
