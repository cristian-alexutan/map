package model.value;

import model.type.Type;

public interface Value {
    boolean equals(Object another);
    Type getType();
}
