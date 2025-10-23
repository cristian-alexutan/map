import java.util.Scanner;
import model.Animal;
import model.Dog;

public class App {
    public static void main(String[] args) {
        /*int n;
        System.out.print("n = ");
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        int s = 0;
        for(int i=1; i<=n; i++) {
            int x;
            x = sc.nextInt();
            s += x;
        }
        System.out.println("the average is " + (double)s/n);*/

        Animal dog = new Dog(12);
        System.out.print("dog has " + dog.getLegCount() + " legs");
    }
}
