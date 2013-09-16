package triest.model;

public class Piece {

    private Shape shape;
    private final boolean canRotate;
    private int rotation = 0; //in 90° steps

    public Piece() {
        this(Shape.getRandom());
    }

    public Piece(Shape shape) {
        this.shape = shape;
        this.canRotate = shape != Shape.O;
    }

    public void rotate(boolean clockwise) {
        if (!canRotate) {
            return;
        }
        rotation += clockwise ? 1 : 3;
        rotation %= 4;
    }

    private Pos rot(int x, int y) {
        switch (rotation) {
            case 0:
                return new Pos(x, y);
            case 1:
                return new Pos(y, getWidth() - 1 - x);
            case 2:
                return new Pos(getWidth() - 1 - x, getHeight() - 1 - y);
            case 3:
                return new Pos(getHeight() - 1 - y, x);
            default:
                throw new AssertionError();
        }
    }

    public boolean get(int x, int y) {
        Pos pos = rot(x, y);
        return shape.get(pos.x, pos.y);
    }

    public int getWidth() {
        return rotation % 2 == 0 ? shape.getWidth() : shape.getHeight();
    }

    public int getHeight() {
        return rotation % 2 == 0 ? shape.getHeight() : shape.getWidth();
    }

    public Pos getCenter() {
        switch(shape) {
            case O : return new Pos(0,0);
            case I : return rotation % 2 == 0 ? new Pos(1,0) : new Pos(0,1);
            default: switch (rotation) {
                case 0: return new Pos(1,0);
                case 3: return new Pos(0,1);
                default: return new Pos(1,1);
            }
        }
    }

}
