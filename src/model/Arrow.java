package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Arrow {
    private BufferedImage kep;
    int pozX, pozY;
    int tipLength;
    int regiX, regiY;
    int eredetiX, eredetiY;
    double vx, vy, aktualisSzog;

    public Arrow(double kezdoSzog, double v, int x, int y) {
        try {
            kep = ImageIO.read(new File("img/nyilvesszo.png"));
        } catch (java.io.IOException e) {
            System.out.println("Nem tudtam betolteni a 'img/nyilvesszo.png' kepet");
            System.exit(1);
        }

        pozX = x;
        pozY = y;
        eredetiX = x;
        eredetiY = y;
        regiX = 0;
        regiY = 0;
        vx = v * cos(kezdoSzog);  //radianban kell legyen a kezdoSzog
        //vy = v * sin(kezdoSzog);
        vy = - v * sin(kezdoSzog);
        aktualisSzog = kezdoSzog;
        tipLength = 24;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getAktualisSzog() {
        return aktualisSzog;
    }

    public void setAktualisSzog(double aktualisSzog) {
        this.aktualisSzog = aktualisSzog;
    }

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    public int getPozX() {
        return pozX;
    }

    public void setPozY(int pozY) {
        this.pozY = pozY;
    }

    public int getPozY() {
        return pozY;
    }

    public int getEredetiX() {
        return eredetiX;
    }

    public int getEredetiY() {
        return eredetiY;
    }

    public BufferedImage getKep() {
        return kep;
    }

    public int getRegiX() {
        return regiX;
    }

    public void setRegiX(int regiX) {
        this.regiX = regiX;
    }

    public int getRegiY() {
        return regiY;
    }

    public void setRegiY(int regiY) {
        this.regiY = regiY;
    }

    public int getTipLength() {
        return tipLength;
    }
}
