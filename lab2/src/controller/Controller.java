package controller;
import model.Entity;
import repository.Repository;

public class Controller {
    private Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    public void addEntity(Entity e) {
        repository.addEntity(e);
    }

    public void removeEntity(Entity e) {
        repository.removeEntity(e);
    }

    public void removeEntity(int index) {
        repository.removeEntity(index);
    }

    public Entity[] getAllEntities() {
        return repository.getAllEntities();
    }

    public Entity[] getHeavyEntities() {
        Entity[] allEntities = repository.getAllEntities();
        int count = 0;
        for (Entity e : allEntities) {
            if (e.isHeavy()) {
                count++;
            }
        }
        Entity[] heavyEntities = new Entity[count];
        int index = 0;
        for (Entity e : allEntities) {
            if (e.isHeavy()) {
                heavyEntities[index++] = e;
            }
        }
        return heavyEntities;
    }
}
