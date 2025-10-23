package model;

public class Book implements Entity {
    final private int weight;

    public Book(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public String toString() {
        return "Book weighing " + this.weight + " grams";
    }
}
