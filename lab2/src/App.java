import view.View;
import controller.Controller;
import repository.ArrayRepo;

public class App {
    public static void main(String[] args) {
        ArrayRepo repository = new ArrayRepo();
        Controller controller = new Controller(repository);
        View view = new View(controller);

        view.run();
    }
}
