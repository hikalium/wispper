import java.util.Random;

public class User {
    private String name;
    private int x, y;

    User(String name) {
        Random randomGenerator = new Random();
        this.name = name;
        this.x = randomGenerator.nextInt(500);
        this.y = randomGenerator.nextInt(500);
    }
	public String getUserName(){
		return this.name;
	}
	public int getDistanceTo(User to)
	{
		return Math.abs(this.x - to.x) + Math.abs(this.y - to.y);
	}
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
    @Override
    public String toString() {
        return name + " " + x + " " + y;
    }
}
