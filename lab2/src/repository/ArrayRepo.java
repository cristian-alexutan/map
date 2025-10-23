package repository;
import model.Entity;

public class ArrayRepo implements Repository {
    private Entity[] entities;
    int size;

    public ArrayRepo() {
        entities = new Entity[10];
        size = 0;
    }

    public void addEntity(Entity e) {
        for(int i = 0; i < size; i++)
            if (entities[i].equals(e))
                throw new RepoDuplicateException("entity already exists");
        if (size >= entities.length) {
            Entity[] newEntities = new Entity[entities.length * 2];
            System.arraycopy(entities, 0, newEntities, 0, entities.length);
            entities = newEntities;
        }
        entities[size++] = e;
    }

    public void removeEntity(Entity e) {
        for (int i = 0; i < size; i++) {
            if (entities[i].equals(e)) {
                entities[i] = entities[size-1];
                entities[size-1] = null;
                size--;
                break;
            }
        }
    }

    public void removeEntity(int index) {
        if (index >= 0 && index < size) {
            entities[index] = entities[size-1];
            entities[size-1] = null;
            size--;
        }
        else {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    public Entity[] getAllEntities() {
        Entity[] result = new Entity[size];
        System.arraycopy(entities, 0, result, 0, size);
        return result;
    }
}
