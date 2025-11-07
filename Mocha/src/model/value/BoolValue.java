package model.value;

import model.type.Type;
import model.type.BoolType;

public class BoolValue implements Value {
    private final boolean value;

    public BoolValue() {
        this.value = false;
    }

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
}
