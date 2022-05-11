package Domain.ADTs;

import java.util.*;
import java.util.stream.Collectors;

import Exception.InvalidKeyException;

public class DictionaryADT<Key, Value> implements IDictionary<Key, Value> {
    protected Map<Key, Value> map;

    public DictionaryADT() {
        this.map = new HashMap<>();
    }

    public DictionaryADT(Map<Key, Value> _map) {
        this.map = _map;
    }

    @Override
    public synchronized void add(Key key, Value value) {
        this.map.put(key, value);
    }

    @Override
    public synchronized void update(Key key, Value value) throws Exception {
        if (this.map.containsKey(key)) {
            this.map.put(key, value);
        } else {
            throw new InvalidKeyException();
        }
    }

    @Override
    public synchronized void remove(Key key) {
        this.map.remove(key);
    }

    @Override
    public synchronized Value search(Key key) throws InvalidKeyException {
        if (this.map.containsKey(key)) {
            return this.map.get(key);
        }
        throw new InvalidKeyException();
    }

    @Override
    public synchronized boolean contains(Key key) {
        return this.map.containsKey(key);
    }

    @Override
    public synchronized String toString() {
        StringBuilder string = new StringBuilder();
        for (Key key : map.keySet()) {
            string.append(key.toString()).append("->").append(this.map.get(key)).append("\n");
        }
        return string.toString();
    }

    @Override
    public synchronized Set<Map.Entry<Key, Value>> entrySet() {
        return map.entrySet();
    }

    public synchronized Collection<Value> values() {
        return map.values();
    }

    public synchronized DictionaryADT<Key, Value> deepCopy() {
        Map<Key, Value> newMap = this.map.entrySet()
                .stream()
                .collect(
                        Collectors
                                .toMap(Map.Entry::getKey,
                                        Map.Entry::getValue));
        return new DictionaryADT<>(newMap);
    }

    public synchronized HashMap<Key, Value> getMapContent() {
        return (HashMap<Key, Value>) map;
    }
}
