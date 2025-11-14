package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringValue) {
            return this.value.equals(((StringValue) another).getValue());
        } else {
            return false;
        }
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value;
    }
}
