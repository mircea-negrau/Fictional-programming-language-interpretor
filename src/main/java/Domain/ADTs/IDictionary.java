package Domain.ADTs;

import Exception.InvalidKeyException;

import java.util.Map;
import java.util.Set;

public interface IDictionary<Key, Value> {

    void add(Key v1, Value v2);

    void update(Key v1, Value v2) throws Exception;

    Value search(Key id) throws InvalidKeyException;

    void remove(Key key);

    boolean contains(Key id);

    String toString();

    Set<Map.Entry<Key, Value>> entrySet();
}
