package model.container;

import exceptions.MochaException;

public interface IDictionary<TKey, TValue> {
    void insert(TKey key, TValue value);
    void remove(TKey key) throws MochaException;
    void update(TKey key, TValue value);
    TValue get(TKey key);
    boolean hasKey(TKey key);
    String toString();
}
