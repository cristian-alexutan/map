package model;

public class Cake implements Entity {
    final private int weight;

    public Cake(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public String toString() {
        return "Cake weighing " + this.weight + " grams";
    }
}
