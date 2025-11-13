package model.container;

import exceptions.MochaDictionaryException;
import exceptions.MochaException;

public interface IDictionary<TKey, TValue> {
    void insert(TKey key, TValue value);

    void remove(TKey key) throws MochaException;

    void update(TKey key, TValue value) throws MochaDictionaryException;

    TValue get(TKey key) throws MochaDictionaryException;

    boolean hasKey(TKey key);

    String toString();

    String keyString();
}
