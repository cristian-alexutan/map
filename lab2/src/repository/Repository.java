package repository;
import model.Entity;

public interface Repository {
    public void addEntity(Entity e);
    public void removeEntity(Entity e);
    public void removeEntity(int index);
    public Entity[] getAllEntities();
}
