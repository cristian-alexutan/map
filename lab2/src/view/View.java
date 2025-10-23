package view;
import controller.Controller;
import model.Entity;
import model.Pig;
import model.Cow;
import model.Bird;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        this.controller.addEntity(new Bird(1));
        this.controller.addEntity(new Pig(170));
        this.controller.addEntity(new Cow(500));
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println();
            System.out.println("1 - add entity");
            System.out.println("2 - remove entity");
            System.out.println("3 - print all entities");
            System.out.println("4 - print heavy entities");
            System.out.println("0 - exit");

            System.out.print(" >>> ");
            int choice;
            try {
                choice = scanner.nextInt();
            }
            catch (Exception e) {
                System.out.println("invalid choice");
                scanner.nextLine();
                continue;
            }

            if(choice == 0)
                break;
            else if (choice == 1) {
                System.out.println("choose entity type: 1 - Bird, 2 - Pig, 3 - Cow");
                int type;
                try {
                    type = scanner.nextInt();
                }
                catch (Exception e) {
                    System.out.println("invalid type");
                    scanner.nextLine();
                    continue;
                }
                if(type < 1 || type > 3) {
                    System.out.println("invalid type");
                    continue;
                }
                System.out.println("enter weight:");
                int weight;
                try {
                    weight = scanner.nextInt();
                }
                catch (Exception e) {
                    System.out.println("invalid weight");
                    scanner.nextLine();
                    continue;
                }
                try {
                    if (type == 1)
                        controller.addEntity(new Bird(weight));
                    else if (type == 2)
                        controller.addEntity(new Pig(weight));
                    else if (type == 3)
                        controller.addEntity(new Cow(weight));
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (choice == 2) {
                Entity[] entities = controller.getAllEntities();
                for (int i = 0; i < entities.length; i++)
                    System.out.println(i + " - " + entities[i]);
                System.out.println("enter index to remove:");
                int index;
                try {
                    index = scanner.nextInt();
                }
                catch (Exception e) {
                    System.out.println("invalid index");
                    scanner.nextLine();
                    continue;
                }
                try {
                    controller.removeEntity(index);
                }
                catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (choice == 3) {
                Entity[] entities = controller.getAllEntities();
                for (Entity e : entities)
                    System.out.println(e);
            }
            else if (choice == 4) {
                Entity[] heavyEntities = controller.getHeavyEntities();
                for (Entity e : heavyEntities)
                    System.out.println(e);
            }
            else {
                System.out.println("invalid choice");
            }
        }
    }
}
