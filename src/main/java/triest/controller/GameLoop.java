package triest.controller;

import triest.model.Grid;
import triest.model.Piece;
import triest.model.Shape;
import triest.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {
    private Grid grid;
    private final View view;

    private long lastLine = System.currentTimeMillis();
    private long lineDelay = 6000;
    private Timer timer;

    public GameLoop(View view) {
        this.view = view;
        this.grid = view.getGrid();
        init();
    }

    private void init() {
        view.reset();
        view.setPiece(new Piece(Shape.T));
        grid.newLine();

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                update();
                render();
            }
        }, 10, 10);
    }

    private void update() {
        long now = System.currentTimeMillis();
        if (now - lastLine > lineDelay) {
            if (! grid.newLine()) {
                timer.cancel();
                if(view.newGame()) {
                    init();
                    return;
                } else {
                    System.exit(0);
                }
            }
            lastLine = now;
            lineDelay--;
        }
    }

    private void render() {
        view.repaint();
    }

}
