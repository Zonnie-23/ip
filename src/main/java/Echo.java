public class Echo implements Instruction{
    private String message = "Error";

    public Echo(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message + "\n";
    }
}
