package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import sk.stuba.fei.uim.oop.controls.GamePanelMouseAdapter;
import sk.stuba.fei.uim.oop.controls.GamePanelMouseMotionAdapter;
import sk.stuba.fei.uim.oop.path.Path;
import sk.stuba.fei.uim.oop.path.Vertex;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int FACTOR = 6;
    @Getter
    private Path path;
    @Getter
    private int squareSide;
    private int subsquareSide;

    GamePanel(Path path) {
        this.path = path;

        setBackground(Color.orange);
        squareSide = GameFrame.GAME_PANEL_SIDE/path.getSize();
        subsquareSide = squareSide/FACTOR;
        
        addMouseMotionListener(new GamePanelMouseMotionAdapter(this));
        addMouseListener(new GamePanelMouseAdapter(this));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < path.getSize(); i++) {
            for (int j = 0; j < path.getSize(); j++) {
                Vertex vxRef = path.getVertices().get(i).get(j);
                int connRegister = vxRef.getConnectionRegister();

                Color colorInside = vxRef.isWet() ? Color.blue : Color.darkGray;
                if(!(connRegister == Vertex.ALL_DIS)) {
                    drawFilledRectHere(j, i, FACTOR/3 - 1, FACTOR/3 - 1, FACTOR/3 + 2, FACTOR/3 + 2, Color.lightGray, g);
                    drawFilledRectHere(j, i, FACTOR/3, FACTOR/3, FACTOR/3, FACTOR/3, colorInside, g);
                }
                if((connRegister & Vertex.RIGHT_EN) != 0) {
                    drawFilledRectHere(j, i, (FACTOR/3)*2, FACTOR/3 - 1, FACTOR/3, FACTOR/3 + 2, Color.lightGray, g);
                    drawFilledRectHere(j, i, (FACTOR/3)*2, FACTOR/3, FACTOR/3, FACTOR/3, colorInside, g);
                }
                if((connRegister & Vertex.TOP_EN) != 0) {
                    drawFilledRectHere(j, i, FACTOR/3 - 1, (FACTOR/3)*2, FACTOR/3 + 2, FACTOR/3, Color.lightGray, g);
                    drawFilledRectHere(j, i, FACTOR/3, (FACTOR/3)*2, FACTOR/3, FACTOR/3, colorInside, g);
                }
                if((connRegister & Vertex.LEFT_EN) != 0) {
                    drawFilledRectHere(j, i, 0, FACTOR/3 - 1, FACTOR/3, FACTOR/3 + 2, Color.lightGray, g);
                    drawFilledRectHere(j, i, 0, FACTOR/3, FACTOR/3, FACTOR/3, colorInside, g);
                }
                if((connRegister & Vertex.BOTTOM_EN) != 0) {
                    drawFilledRectHere(j, i, FACTOR/3 - 1, 0, FACTOR/3 + 2, FACTOR/3, Color.lightGray, g);
                    drawFilledRectHere(j, i, FACTOR/3, 0, FACTOR/3, FACTOR/3, colorInside, g);
                }
                if (vxRef.equals(path.getStartVertex()))
                    drawHollowRectHere(j, i, Color.green, g);
                if (vxRef.equals(path.getFinishVertex()))
                    drawHollowRectHere(j, i, Color.red, g);
                if(vxRef.isMarkedForHighlight())
                    drawHollowRectHere(j, i, Color.black, g);
            }
        }
    }
    private void drawFilledCubeHere(int x, int y, int subX, int subY, Color c, Graphics g){
        g.setColor(c);
        int a = squareSide*x + subsquareSide*subX;
        int b = (squareSide*path.getSize() - subsquareSide) - (squareSide*y + subsquareSide*subY);
        g.fillRect(a, b, subsquareSide, subsquareSide);
    }
    private void drawFilledRectHere(int x, int y, int subX, int subY, int xExtrusion, int yExtrusion, Color c, Graphics g){
        g.setColor(c);
        for(int i = 0; i < yExtrusion; i++)
            for(int j = 0; j < xExtrusion; j++)
                drawFilledCubeHere(x, y, subX + j, subY + i, c, g);
    }
    private void drawHollowRectHere(int x, int y, Color c, Graphics g){
        drawFilledRectHere(x, y, 0, 0, FACTOR, 1, c, g);
        drawFilledRectHere(x, y, 0, 0, 1, FACTOR, c, g);

        drawFilledRectHere(x, y, 0, FACTOR - 1, FACTOR, 1, c, g);
        drawFilledRectHere(x, y, FACTOR - 1, 0, 1, FACTOR, c, g);
    }
}
