package model.type;

public class IntType implements Type {
    @Override
    public boolean equals(Object another) {
        if(another instanceof IntType) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public model.value.Value defaultValue() {
        return new model.value.IntValue(0);
    }
}
