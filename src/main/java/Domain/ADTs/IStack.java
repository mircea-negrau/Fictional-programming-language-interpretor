package Domain.ADTs;

import Exception.IsEmptyException;

public interface IStack<Type> {
    void push(Type element);

    Type pop() throws IsEmptyException;

    boolean isEmpty();

    String toString();
}
