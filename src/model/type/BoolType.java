package model.type;

import model.value.Value;
import model.value.BoolValue;

public class BoolType implements Type {
    private static final BoolValue DEFAULT_VALUE = new model.value.BoolValue(false);

    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolType) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return DEFAULT_VALUE;
    }
}
