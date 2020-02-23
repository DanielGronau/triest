package triest;

import triest.controller.GameLoop;
import triest.model.Grid;
import triest.view.Gui;
import triest.view.View;

public class Triest {
    public static void main(String[] args) {
        View view = new Gui().getView(new Grid(10, 20)); //view
        new GameLoop(view);  //controller
    }
}
