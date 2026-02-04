package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Stickman {
    private BufferedImage stickman;
    private int pozX, pozY, armLength;

    public Stickman(int x, int y) {
        try {
            stickman = ImageIO.read(new File("img/stickman_draw.png"));
        } catch (java.io.IOException e) {
            System.out.println("Nem tudtam betolteni a 'img/stickman_draw.png' kepet");
            System.exit(1);
        }
        pozX = x;
        pozY = y;
        setShortArm();
    }

    public BufferedImage getStickman() {
        return stickman;
    }

    public int getPozX() {
        return pozX;
    }

    public int getPozY() {
        return pozY;
    }

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    public void setPozY(int pozY) {
        this.pozY = pozY;
    }

    public void setLongArm() {
        armLength = 100;
    }

    public void setShortArm() {
        armLength = 70;
    }

    public int getArmLength() {
        return armLength;
    }
}
