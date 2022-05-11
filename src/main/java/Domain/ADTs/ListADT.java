package Domain.ADTs;

import java.util.ArrayList;
import java.util.List;

import Exception.IsEmptyException;
import Exception.InvalidKeyException;

public class ListADT<Type> implements IList<Type> {
    private final List<Type> list;

    public ListADT() {
        this.list = new ArrayList<>();
    }

    public ListADT(List<Type> initialItems) {
        this.list = initialItems;
    }

    @Override
    public synchronized void append(Type element) {
        list.add(element);
    }

    @Override
    public synchronized Type pop() throws Exception {
        if (this.list.size() > 0)
            return list.remove(0);
        throw new IsEmptyException();
    }

    @Override
    public synchronized void clear() {
        list.clear();
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }

    public synchronized int size() {
        return list.size();
    }

    public synchronized Type get(int index) throws InvalidKeyException {
        if (index < this.list.size())
            return list.get(index);
        throw new InvalidKeyException();
    }

    public synchronized String toString() {
        StringBuilder string = new StringBuilder();
        for (Type item : list) {
            string.append(item.toString()).append("\n");
        }
        return string.toString();
    }

    public synchronized List<Type> toList() {
        return this.list;
    }
}
