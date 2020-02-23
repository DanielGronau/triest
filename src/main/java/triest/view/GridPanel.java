package triest.view;

import triest.model.Grid;
import triest.model.Piece;
import triest.model.Pos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GridPanel extends JPanel implements View {
    private final static int BLOCK_SIZE = 30;

    private final Dimension size;
    private final Color pieceColor = new Color(80, 80, 255, 150);
    private final Color hudColor = new Color(220, 220, 150, 150);
    private final Font hudFont = new Font("Monospaced", Font.BOLD, 20);

    private final Grid grid;
    private Piece piece;
    private int posX = 0;
    private int posY = 0;

    GridPanel(final Grid grid) {
        this.grid = grid;
        this.size = new Dimension(grid.width * BLOCK_SIZE, grid.height * BLOCK_SIZE);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                posX = e.getX() / BLOCK_SIZE;
                posY = e.getY() / BLOCK_SIZE;
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (piece == null) {
                    return;
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (piece != null) piece.rotate(true);
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    grid.panic();
                } else if (SwingUtilities.isLeftMouseButton(e) && grid.remove(posX, posY, piece)) {
                    piece = new Piece();
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean newGame() {
        return JOptionPane.showConfirmDialog(this, "Play again?", "Game Over!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBlocks(g2);
        drawPiece(g2);
        drawGrid(g2);
        drawHud(g2);
    }

    private void drawBlocks(Graphics2D g2) {
        g2.fillRect(0, 0, size.width, size.height);
        for (int y = 0; y < grid.height; y++) {
            double f = (double) y / grid.height;
            int green = (int) (120 + f * (240 - 120));
            int red = (int) (120 + (1 - f) * (240 - 120));
            Color blockColor = new Color(red, green, 100);
            for (int x = 0; x < grid.width; x++) {
                if (grid.get(x, y)) {
                    g2.setPaint(new GradientPaint(
                        x * BLOCK_SIZE, y * BLOCK_SIZE, blockColor,
                        (x + 1) * BLOCK_SIZE, (y + 1) * BLOCK_SIZE, blockColor.darker())
                    );
                    g2.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g2.setPaint(null);
                }
            }
        }
    }

    private void drawPiece(Graphics2D g2) {
        if (piece == null) return;
        Pos center = piece.getCenter();
        for (int x = 0; x < piece.getWidth(); x++) {
            for (int y = 0; y < piece.getHeight(); y++) {
                if (piece.get(x, y)) {
                    int cx = x + posX - center.x;
                    int cy = y + posY - center.y;
                    g2.setColor(pieceColor);
                    g2.fillRect(cx * BLOCK_SIZE, cy * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    private void drawGrid(Graphics2D g2) {
        for (int y = 0; y < grid.height; y++) {
            for (int x = 0; x < grid.width; x++) {
                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    private void drawHud(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(hudFont);
        g2.setColor(hudColor);
        g2.drawString(String.format("%05d pieces, %03d lines", grid.getPieceCount(), grid.getLineCount()), 10, 20);
    }
}
