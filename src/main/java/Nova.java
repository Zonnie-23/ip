import java.util.Scanner;

public class Nova {
    private static final String HORIZONTAL_BAR = "    ____________________________________________________________\n";

    public static void main(String[] args) {
        // Greet User
        System.out.println(HORIZONTAL_BAR
                + "    Hello! I'm Nova.\n"
                + "    What can I do for you?\n"
                + HORIZONTAL_BAR);

        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;

        while (isActive) {
            String msg = scanner.nextLine();
            System.out.println(HORIZONTAL_BAR);
            Instruction instruction;
            switch (msg.toUpperCase()) {
                case "BYE":
                    isActive = false;
                    instruction = new Exit();
                    break;
                default:
                    instruction = new Echo(msg);
                    break;
            }
            System.out.println("    " + instruction + HORIZONTAL_BAR);
        }
    }
}
