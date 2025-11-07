package view;

import model.statement.*;
import model.programstate.*;
import model.value.*;
import model.container.*;
import model.type.*;
import model.expression.*;
import repository.*;
import controller.*;
import utils.HardcodedPrograms;

import java.util.Scanner;

public class View {
    private Controller controller;
    HardcodedPrograms hc = new HardcodedPrograms();

    public void printMenu() {
        System.out.println("0 - exit");
        System.out.println("1 - int v; v = 2; print(v);");
        System.out.println("2 - int a; int b; a=2+3*5; b=a+1; print(b);");
        System.out.println("3 - bool a; int v; a=true; (if a then v=2 else v=3); print(v)");
        System.out.println("-----------------------------");
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.print(" >>> ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            if (choice.equals("0")) {
                break;
            }

            IStack<Statement> exeStack = new MochaStack<>();
            IDictionary<String, Value> symTable = new MochaDictionary<>();
            IList<Value> out = new MochaList<>();
            ProgramState programState = new ProgramState(exeStack, symTable, out);
            IRepository repository = new Repository(programState);
            controller = new Controller(repository, true);

            try {
                switch (choice) {
                    case "1":
                        exeStack.push(hc.getStatement(0));
                        controller.allSteps();
                        break;
                    case "2":
                        exeStack.push(hc.getStatement(1));
                        controller.allSteps();
                        break;
                    case "3":
                        exeStack.push(hc.getStatement(2));
                        controller.allSteps();
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            }
        }
    }
}
