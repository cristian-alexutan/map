package model.container;

import exceptions.MochaListException;

import java.util.ArrayList;

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
        if (list.isEmpty()) throw new MochaListException("List is empty");
        return list.removeFirst();
    }

    @Override
    public T front() throws MochaListException {
        if (list.isEmpty()) throw new MochaListException("List is empty");
        return list.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (T val : list)
            ans.append(val.toString()).append("\n");
        return ans.toString();
    }

    @Override
    public IList<T> deepCopy() {
        MochaList<T> newList = new MochaList<>();
        for (T val : list) {
            newList.add(val);
        }
        return newList;
    }
}
