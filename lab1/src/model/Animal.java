package model;

public class Animal {
    protected int weight;
    protected int legCount;

    public Animal(int weight) {
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getLegCount() {
        return legCount;
    }
}
