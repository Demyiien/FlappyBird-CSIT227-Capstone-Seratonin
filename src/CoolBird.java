public class CoolBird extends Bird {

    public CoolBird() {
        super("CoolBird");
    }

    public CoolBird(String name) {
        super(name);
    }

    public CoolBird(String name, Point startPos, int cellSize) {
        super(name, startPos, cellSize);
    }

    @Override
    public void jump() {
        super.jump();
    }

    @Override
    public void die() {
        super.die();
    }
}
