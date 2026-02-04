package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bow {
    private BufferedImage alaphelyzet, fulldraw;
    private BufferedImage aktualis;
    private double rotAngle;
    private int pozX,pozY;

    public Bow(int x, int y){
        try {
            alaphelyzet = ImageIO.read(new File("img/fuggolegesalaphelyzet.png"));
        } catch (java.io.IOException e) {
            System.out.println("Nem tudtam betolteni a 'img/fuggolegesalaphelyzet.png' kepet");
            System.exit(1);
        }

        try {
            fulldraw = ImageIO.read(new File("img/fulldraw.png"));
        } catch (java.io.IOException e) {
            System.out.println("Nem tudtam betolteni a 'img/fulldraw.png' kepet");
            System.exit(1);
        }
        pozX = x;
        pozY = y;
        aktualis = alaphelyzet;
    }

    public void toAlaphelyzet(){
        aktualis = alaphelyzet;
    }

    public void toFullDraw(){
        aktualis = fulldraw;
    }

    public BufferedImage getAktualis() {
        return aktualis;
    }

    public double getRotAngle() {
        return rotAngle;
    }

    public void setRotAngle(double rotAngle) {
        this.rotAngle = rotAngle;
    }

    public int getPozX() {
        return pozX;
    }

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    public int getPozY() {
        return pozY;
    }

    public void setPozY(int pozY) {
        this.pozY = pozY;
    }
}
