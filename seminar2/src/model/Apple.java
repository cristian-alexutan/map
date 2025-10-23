package model;

public class Apple implements Entity {
    final private int weight;

    public  Apple(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public String toString() {
        return "Apple weighing " + this.weight + " grams";
    }
}
