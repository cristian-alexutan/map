package model.container;
import java.util.Stack;

import exceptions.MochaException;

public class MochaStack<T> implements IStack<T> {
    Stack<T> stack;

    public MochaStack() {
        stack = new Stack<>();
    }

    @Override
    public void push(T val) {
        stack.push(val);
    }

    @Override
    public T pop() throws MochaException {
        if(stack.isEmpty()) throw new MochaException("Stack is empty");
        return stack.pop();
    }

    @Override
    public T top() throws MochaException {
        if(stack.isEmpty()) throw new MochaException("Stack is empty");
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
