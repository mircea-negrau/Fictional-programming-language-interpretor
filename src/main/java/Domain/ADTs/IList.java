package Domain.ADTs;

import Exception.InvalidKeyException;

public interface IList<Type> {
    void append(Type element);

    Type pop() throws Exception;

    void clear();

    boolean isEmpty();

    int size();

    Type get(int index) throws InvalidKeyException;

    String toString();
}
