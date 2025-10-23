package model;

public class Pig implements Entity {
    private final int weight;

    public Pig(int weight) {
        this.weight = weight;
    }

    public boolean isHeavy() {
        return this.weight > 3;
    }

    public String toString() {
        return "Pig with weight " +  this.weight;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Pig)) return false;
        Pig pig = (Pig) o;
        return weight == pig.weight;
    }
}
