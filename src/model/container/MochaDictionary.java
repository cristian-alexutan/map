package model.container;

import exceptions.MochaDictionaryException;

import java.util.HashMap;
import java.util.Map;

public class MochaDictionary<TKey, TValue> implements IDictionary<TKey, TValue> {
    Map<TKey, TValue> map;

    public MochaDictionary() {
        map = new HashMap<>();
    }

    @Override
    public void insert(TKey key, TValue value) {
        map.put(key, value);
    }

    @Override
    public void remove(TKey key) throws MochaDictionaryException {
        if (!map.containsKey(key)) throw new MochaDictionaryException("Key not found: " + key);
        map.remove(key);
    }

    @Override
    public void update(TKey key, TValue value) throws MochaDictionaryException {
        if (!map.containsKey(key)) throw new MochaDictionaryException("Key not found: " + key);
        map.put(key, value);
    }

    @Override
    public TValue get(TKey key) {
        return map.get(key);
    }

    @Override
    public boolean hasKey(TKey key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (TKey key : map.keySet())
            ans.append(key.toString()).append(" -> ").append(map.get(key).toString()).append("\n");
        return ans.toString();
    }

    @Override
    public String keyString() {
        StringBuilder ans = new StringBuilder();
        for (TKey key : map.keySet())
            ans.append(key.toString()).append("\n");
        return ans.toString();
    }

    @Override
    public Map<TKey, TValue> getContent() {
        return map;
    }

    @Override
    public MochaDictionary<TKey, TValue> deepCopy() {
        MochaDictionary<TKey, TValue> newDict = new MochaDictionary<>();
        for (TKey key : map.keySet()) {
            newDict.insert(key, map.get(key));
        }
        return newDict;
    }
}
