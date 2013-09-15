package triest.model;

import java.util.Arrays;
import java.util.Random;

public class Grid {
    public final int width;
    public final int height;
    private final boolean[][] grid;
    private final Random random = new Random();


    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new boolean[height][width];
    }

    //returns false on "game over"
    public boolean newLine() {
        for (boolean isBlock : grid[0]) {
            if (isBlock) return false; //highest line contains block -> game over
        }
        //System.arraycopy(grid, 1, grid, 0, height - 1); //move blocks up
        for (int y = 0; y < height - 1; y++) {
            System.arraycopy(grid[y + 1], 0, grid[y], 0, width);
        }
        Arrays.fill(grid[height - 1], true); //fill lowest line with blocks
        return true;
    }

    public boolean get(int x, int y) {
        return grid[y][x];
    }

    private void set(int x, int y, boolean value) {
        grid[y][x] = value;
    }

    //tries to remove the piece from the given Position
    public boolean remove(int x, int y, Piece piece) {
        int cx = x - piece.getCenterX();
        int cy = y - piece.getCenterY();
        if (cx < 0 || cy < 0 || cx + piece.getWidth() > width || cy + piece.getHeight() > height) {
            return false; //not completely inside grid
        }
        for (int px = 0; px < piece.getWidth(); px++) {
            for (int py = 0; py < piece.getHeight(); py++) {
                if (piece.get(px, py) && !get(cx + px, cy + py)) {
                    return false; //part of piece not over block
                }
            }
        }
        for (int px = 0; px < piece.getWidth(); px++) {
            for (int py = 0; py < piece.getHeight(); py++) {
                if (piece.get(px, py) &&
                        (py == 0 || !piece.get(px, py - 1)) &&
                        cy + py != 0 && get(cx + px, cy + py - 1)) {
                    return false; //piece not on top
                }
            }
        }
        for (int px = 0; px < piece.getWidth(); px++) {
            for (int py = 0; py < piece.getHeight(); py++) {
                if (piece.get(px, py)) {
                    set(cx + px, cy + py, false); //remove block under piece
                }
            }
        }
        return true;
    }

    //drops some random blocks to help the player if she is stuck
    public void panic() {
        for (int x = 0; x < width; x++) {
            if (random.nextBoolean()) {
                for (int y = 1; y < height; y++) {
                    if (get(x, y)) {
                        set(x, y - 1, true);
                    }
                }
            }
        }
    }

    public void reset() {
        for(boolean[] row : grid) {
            Arrays.fill(row, false);
        }
    }
}
