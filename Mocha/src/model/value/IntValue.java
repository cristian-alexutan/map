package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
    private final int value;

    public IntValue() {
        this.value = 0;
    }

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof IntValue) {
            return this.value == ((IntValue) another).getValue();
        } else {
            return false;
        }
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
