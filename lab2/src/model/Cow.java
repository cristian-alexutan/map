package model;

public class Cow implements Entity {
    private final int weight;

    public Cow(int weight) {
        this.weight = weight;
    }

    public boolean isHeavy() {
        return this.weight > 3;
    }

    public String toString() {
        return "Cow with weight " +  this.weight;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Cow)) return false;
        Cow cow = (Cow) o;
        return weight == cow.weight;
    }
}