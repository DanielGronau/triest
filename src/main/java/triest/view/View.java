package triest.view;

import triest.model.Grid;
import triest.model.Piece;

public interface View {

    void repaint();

    void setPiece(Piece piece);

    boolean newGame();

    Grid getGrid();

}
