import java.util.Random;

public class User {
    private String name;
    private int x, y;

    User(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    User() {
        Random randomGenerator = new Random();
        name = "tester" + randomGenerator.nextInt(100);
        x = randomGenerator.nextInt(1000);
        y = randomGenerator.nextInt(1000);
    }

    @Override
    public String toString() {
        return name + " " + x + " " + y;
    }
}
