package triest.model;

import java.util.Random;

public enum Shape {
    I(new boolean[][]{{true, true, true, true}}),
    J(new boolean[][]{{true, true, true},{false, false, true}}),
    L(new boolean[][]{{true, true, true},{true, false, false}}),
    O(new boolean[][]{{true, true}, {true, true}}),
    S(new boolean[][]{{false, true, true}, {true, true, false}}),
    T(new boolean[][]{{true, true, true}, {false, true, false}}),
    Z(new boolean[][]{{true, true, false},{false, true, true}});

    private static final Random random = new Random();

    boolean[][] shape;

    Shape(boolean[][] shape) {
       this.shape = shape;
    }

    public static Shape getRandom() {
        return values()[random.nextInt(values().length)];
    }
}
