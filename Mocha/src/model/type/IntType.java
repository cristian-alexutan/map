package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type {
    private static final IntValue DEFAULT_VALUE = new IntValue(0);

    @Override
    public boolean equals(Object another) {
        if (another instanceof IntType) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return DEFAULT_VALUE;
    }
}
