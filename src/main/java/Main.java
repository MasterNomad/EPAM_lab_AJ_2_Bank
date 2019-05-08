import demo.Demo;
import demo.Filler;


public class Main {

    public static void main(String[] args) {
        new Filler().fillAccounts("data", 10);
        new Demo().execute();
    }

}
