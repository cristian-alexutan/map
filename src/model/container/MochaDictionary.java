package model.container;

import java.util.HashMap;
import exceptions.MochaDictionaryException;

public class MochaDictionary<TKey, TValue> implements IDictionary<TKey, TValue> {
    HashMap<TKey, TValue> map;

    public MochaDictionary() {
        map = new HashMap<>();
    }

    @Override
    public void insert(TKey key, TValue value) {
        map.put(key, value);
    }

    @Override
    public void remove(TKey key) throws MochaDictionaryException {
        if(!map.containsKey(key)) throw new MochaDictionaryException("Key not found: " + key);
        map.remove(key);
    }

    @Override
    public void update(TKey key, TValue value) throws MochaDictionaryException {
        if(!map.containsKey(key)) throw new MochaDictionaryException("Key not found: " + key);
        map.put(key, value);
    }

    @Override
    public TValue get(TKey key) throws MochaDictionaryException {
        if(!map.containsKey(key)) throw new MochaDictionaryException("Key not found: " + key);
        return map.get(key);
    }

    @Override
    public boolean hasKey(TKey key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
