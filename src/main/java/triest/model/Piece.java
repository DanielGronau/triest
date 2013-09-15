package triest.model;

public class Piece {

    private boolean[][] shape;
    private final boolean canRotate;
    private int centerX = 1;
    private int centerY = 0;

    public Piece() {
        this(Shape.getRandom());
    }

    public Piece(Shape pattern) {
        this.shape = pattern.shape;
        this.canRotate = pattern != Shape.O;
    }

    public void rotate(boolean clockwise) {
        if (!canRotate) {
            return;
        }
        if (!clockwise) {
            rotate(true);
            rotate(true);
            rotate(true);
        } else {
            int width = shape[0].length;
            int height = shape.length;
            boolean[][] newShape = new boolean[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    newShape[x][height - 1 - y] = shape[y][x];
                }
            }
            shape = newShape;
            int newX = height - 1 - centerY;
            int newY = centerX;
            centerX = newX;
            centerY = newY;
        }
    }

    public boolean get(int x, int y) {
        return shape[y][x];
    }

    public int getWidth() {
        return shape[0].length;
    }
    public int getHeight() {
        return shape.length;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }
}
