package model.type;

import model.value.Value;
import model.value.StringValue;

public class StringType implements Type {
    @Override
    public boolean equals(Object another) {
        if (another instanceof StringType) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
