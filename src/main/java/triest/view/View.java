package triest.view;

import triest.model.Grid;
import triest.model.Piece;

public interface View {

    public void repaint();

    public void setPiece(Piece piece);

    public boolean newGame();

    public Grid getGrid();

}
