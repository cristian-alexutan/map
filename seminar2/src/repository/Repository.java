package repository;
import model.Entity;

public class Repository {
    private Entity[] entities;
    private int cnt;

    public Repository() {
        entities = new Entity[10];
    }

    public void addEntity(Entity o) {
        if(cnt == entities.length)
            throw new ArrayIndexOutOfBoundsException();
        entities[cnt++] = o;
    }

    public Entity[] getObjects() {
        Entity[] result = new Entity[cnt];
        System.arraycopy(entities, 0, result, 0, cnt);
        return result;
    }
}
