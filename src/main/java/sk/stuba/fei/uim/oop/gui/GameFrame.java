package sk.stuba.fei.uim.oop.gui;

import lombok.Setter;
import sk.stuba.fei.uim.oop.controls.GameFrameActionListener;
import sk.stuba.fei.uim.oop.controls.GameFrameChangeListener;
import sk.stuba.fei.uim.oop.controls.GameFrameKeyAdapter;
import sk.stuba.fei.uim.oop.path.Path;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    static final int GAME_PANEL_SIDE = 720;
    private static final int TOP_PANEL_HEIGHT = 48;
    public static final String SIZE_SLIDER_ID = "SIZE_SLIDER";
    public static final String RESET_CAPTION = "RESET";
    public static final String CHECK_CAPTION = "CHECK";
    private int levelCounter;
    @Setter
    private int size;
    private JLabel statusLabel;
    private Path path;
    private GamePanel gamePanel;
    public GameFrame() {
        setTitle("Pipes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GAME_PANEL_SIDE, GAME_PANEL_SIDE + TOP_PANEL_HEIGHT);
        setResizable(false);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new GameFrameKeyAdapter(this));

        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        topPanel.setPreferredSize(new Dimension(GAME_PANEL_SIDE, TOP_PANEL_HEIGHT));
        JSlider sizeSlider = new JSlider(8, 12, 8);
        sizeSlider.setName(SIZE_SLIDER_ID);
        sizeSlider.setMajorTickSpacing(2);
        sizeSlider.setSnapToTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.setFocusable(false);
        sizeSlider.addChangeListener(new GameFrameChangeListener(this));

        levelCounter = 1;
        size = sizeSlider.getValue();
        statusLabel = new JLabel();
        updateStatusLabel();

        JButton resetButton = new JButton(RESET_CAPTION);
        resetButton.setFocusable(false);
        resetButton.addActionListener(new GameFrameActionListener(this));
        JButton checkButton = new JButton(CHECK_CAPTION);
        checkButton.setFocusable(false);
        checkButton.addActionListener(new GameFrameActionListener(this));

        path = new Path(size);
        gamePanel = new GamePanel(path);
        gamePanel.setPreferredSize(new Dimension(GAME_PANEL_SIDE, GAME_PANEL_SIDE));

        topPanel.add(statusLabel);
        topPanel.add(sizeSlider);
        topPanel.add(resetButton);
        topPanel.add(checkButton);

        add(topPanel, BorderLayout.PAGE_START);
        add(gamePanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
    private void updateStatusLabel(){
        statusLabel.setText("Level: " + levelCounter + ", Size: " + size + "x" + size);
    }
    public void resetGame(boolean successfully){
        levelCounter = successfully ? levelCounter + 1 : 1;
        updateStatusLabel();

        path = new Path(size);
        remove(gamePanel);
        gamePanel = new GamePanel(path);
        add(gamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    public void checkGame(){
        if(path.floodPathTillBlockage())
            resetGame(true);
        else
            gamePanel.repaint();
    }
}
