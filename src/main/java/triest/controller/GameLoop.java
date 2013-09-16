package triest.controller;

import triest.model.Piece;
import triest.model.Shape;
import triest.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {
    private final View view;

    private long lastLine = System.currentTimeMillis();
    private long lineDelay = 6000;
    private Timer timer;

    public GameLoop(View view) {
        this.view = view;
        init();
    }

    private void init() {
        view.getGrid().reset();
        view.setPiece(new Piece(Shape.T));
        view.getGrid().newLine();

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
            if (!view.getGrid().newLine()) {
                timer.cancel();
                if (view.newGame()) {
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
