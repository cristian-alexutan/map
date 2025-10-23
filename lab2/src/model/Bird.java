package model;

public class Bird implements Entity {
    private final int weight;

    public Bird(int weight) {
        this.weight = weight;
    }

    public boolean isHeavy() {
        return this.weight > 3;
    }

    public String toString() {
        return "Bird with weight " +  this.weight;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Bird)) return false;
        Bird bird = (Bird) o;
        return weight == bird.weight;
    }
}
