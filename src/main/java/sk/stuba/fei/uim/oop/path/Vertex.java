package sk.stuba.fei.uim.oop.path;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Vertex {
    public static final int ALL_DIS = 0b0000;
    public static final int RIGHT_EN = 0b0001;
    public static final int TOP_EN = 0b0010;
    public static final int LEFT_EN = 0b0100;
    public static final int BOTTOM_EN = 0b1000;
    static final int CNR_BIT_WIDTH = 4;
    private int x;
    private int y;
    private boolean wet;
    private ArrayList<Vertex> connectionReferences;
    private int connectionRegister;
    private boolean markedForHighlight;
    Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        wet = false;
        connectionReferences = new ArrayList<>();
        connectionRegister = ALL_DIS;
        markedForHighlight = false;
    }
    void flood(){
        wet = true;
    }
    void drain(){
        wet = false;
    }
    void addConnectionReference(Vertex vertex){
        connectionReferences.add(vertex);
    }
    void enableConnection(Vertex vertex){
        int fx = vertex.getX();
        int fy = vertex.getY();
        if(fx > x)
            connectionRegister |= RIGHT_EN;
        if(fy > y)
            connectionRegister |= TOP_EN;
        if(fx < x)
            connectionRegister |= LEFT_EN;
        if(fy < y)
            connectionRegister |= BOTTOM_EN;
    }
    void disableConnection(Vertex vertex){
        int fx = vertex.getX();
        int fy = vertex.getY();
        if(fx > x)
            connectionRegister &= ~RIGHT_EN;
        if(fy > y)
            connectionRegister &= ~TOP_EN;
        if(fx < x)
            connectionRegister &= ~LEFT_EN;
        if(fy < y)
            connectionRegister &= ~BOTTOM_EN;
    }
    public void rotate(){
        int highestBit = connectionRegister & (1 << (CNR_BIT_WIDTH - 1));
        connectionRegister <<= 1;
        connectionRegister &= (1 << CNR_BIT_WIDTH) - 1;
        connectionRegister |= highestBit >> (CNR_BIT_WIDTH - 1);
    }
    public void markForHighlight(){
        markedForHighlight = true;
    }
    public void unmarkForHighlight(){
        markedForHighlight = false;
    }
}
