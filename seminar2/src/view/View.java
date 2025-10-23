package view;
import controller.Controller;

public class View {
    private  Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }
    public void run() {
        Object[] ans = this.controller.getHeavierThan200();
        for (Object o : ans)
            System.out.println(o.toString());
    }
}
