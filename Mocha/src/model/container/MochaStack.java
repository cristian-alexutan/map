package model.container;
import java.util.Stack;

import exceptions.MochaStackException;

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
    public T pop() throws MochaStackException {
        if(stack.isEmpty()) throw new MochaStackException("Stack is empty");
        return stack.pop();
    }

    @Override
    public T top() throws MochaStackException {
        if(stack.isEmpty()) throw new MochaStackException("Stack is empty");
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for(T val : stack)
            ans.insert(0, val.toString() + "\n");
        return ans.toString();
    }
}
