package view;

import javax.swing.*;
import java.awt.*;

public class Target extends JPanel {
    private int pozX,pozY;
    public Target(int x, int y){
        this.pozX = x;
        this.pozY = y;
        setBounds(x,y,30,300);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        int hper5 = h/5;
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,w,hper5);
        g.setColor(Color.ORANGE);
        g.fillRect(0,hper5,w,hper5);
        g.setColor(Color.RED);
        g.fillRect(0,2*hper5,w,hper5);
        g.setColor(Color.ORANGE);
        g.fillRect(0,3*hper5,w,hper5);
        g.setColor(Color.YELLOW);
        g.fillRect(0,4*hper5,w,hper5);
    }

    public int getPozX(){
        return pozX;
    }
    public int getPozY(){
        return pozY;
    }
    public void setPozX(int pozX) {
        this.pozX = pozX;
    }
    public void setPozY(int pozY) {
        this.pozY = pozY;
    }
}
