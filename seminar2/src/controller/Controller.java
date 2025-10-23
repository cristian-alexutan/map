package controller;
import repository.Repository;
import model.Entity;

public class Controller {
    private Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    public void addEntity(Entity e) {
        repository.addEntity(e);
    }

    public Entity[] getHeavierThan200() {
        Entity[] entities = this.repository.getObjects();
        Entity[] ans = new Entity[10];
        int len = 0;

        for (Entity o : entities)
            if (o.getWeight() > 200)
                ans[len++] = o;

        Entity[] ansfinal = new Entity[len];
        System.arraycopy(ans, 0, ansfinal, 0, len);
        return ansfinal;
    }
}
