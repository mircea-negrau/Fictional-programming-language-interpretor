package Domain.ADTs;

import java.util.*;

import Exception.IsEmptyException;

public class StackADT<Type> implements IStack<Type> {
    Deque<Type> stack;

    public StackADT() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public synchronized void push(Type element) {
        stack.push(element);
    }

    @Override
    public synchronized Type pop() throws IsEmptyException {
        if (stack.isEmpty())
            throw new IsEmptyException();
        return stack.pop();
    }

    public synchronized Type getTop() {
        return this.stack.peek();
    }

    @Override
    public synchronized boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public synchronized String toString() {
        StringBuilder string = new StringBuilder();
        for (Type item : stack) {
            string.append(item.toString()).append("\n");
        }
        return string.toString();
    }

    public synchronized StackADT<Type> shallowCopy() {
        StackADT<Type> copy_stack = new StackADT<>();
        ArrayDeque<Type> aux_stack = new ArrayDeque<>();
        LinkedList<Type> buffer_list = new LinkedList<>();

        while (!this.stack.isEmpty()) {
            Type elem = this.stack.pop();
            buffer_list.addLast(elem);
        }
        Iterator<Type> descending_iterator = buffer_list.descendingIterator();

        while (descending_iterator.hasNext()) {
            Type elem = descending_iterator.next();
            copy_stack.push(elem);
            aux_stack.push(elem);
        }

        this.stack = aux_stack;
        return copy_stack;
    }
}