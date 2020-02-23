package triest.view;

import triest.model.Grid;

import javax.swing.*;
import java.awt.*;

public class Gui {

    //returns the component that needs to be repainted
    public View getView(Grid grid) {
        final GridPanel gridPanel = new GridPanel(grid);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Triest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 1));
            frame.add(gridPanel);
            frame.setResizable(false);
            frame.pack();
            centerOnScreen(frame);
            frame.setVisible(true);
        });
        return gridPanel;
    }

    private void centerOnScreen(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - frame.getSize().width) / 2;
        int y = (dim.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
    }
}
