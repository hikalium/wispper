import java.util.Random;

public class User {
    int id;
    String name;
    int x, y;

    User(int id, String name, int x, int y) {
        this.id = id;
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