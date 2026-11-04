package sk.stuba.fei.uim.oop.controls;

import lombok.AllArgsConstructor;
import sk.stuba.fei.uim.oop.gui.GamePanel;
import sk.stuba.fei.uim.oop.path.Path;
import sk.stuba.fei.uim.oop.path.Vertex;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@AllArgsConstructor
public class GamePanelMouseAdapter extends MouseAdapter {
    private GamePanel gamePanel;

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int squareSide = gamePanel.getSquareSide();
        Path path = gamePanel.getPath();

        int pressedColumn = x/squareSide;
        int pressedRow = (path.getSize() - 1) - y/squareSide;
        if (!path.isOutOfBounds(pressedColumn, pressedRow)) {
            Vertex vxRef = path.getVertices().get(pressedRow).get(pressedColumn);
            if(!(vxRef.getConnectionRegister() == Vertex.ALL_DIS))
                path.drainEntirePath();
            vxRef.rotate();
        }
        gamePanel.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Path path = gamePanel.getPath();

        for(ArrayList<Vertex> row : path.getVertices())
            for(Vertex vertex : row)
                vertex.unmarkForHighlight();
        gamePanel.repaint();
    }
}
