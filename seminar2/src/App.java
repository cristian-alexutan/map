import repository.Repository;
import controller.Controller;
import view.View;
import model.Apple;
import model.Cake;
import model.Book;
import model.Entity;

public class App {
    static void main(String args[]) {
        Repository repository = new Repository();
        Entity a = new Apple(199);
        Entity c = new Cake(300);
        Entity b = new Book(500);
        repository.addEntity(a);
        repository.addEntity(b);
        repository.addEntity(c);
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.run();
    }
}