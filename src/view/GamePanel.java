package view;

import model.Arrow;
import model.Bow;
import model.CurrentScore;
import model.Stickman;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static java.lang.Math.sin;

public class GamePanel extends JPanel {
    private Arrow arrow;
    private Bow bow;
    private CurrentScore currentScore;
    private Stickman stickman;

    public GamePanel(Bow bow, Stickman stickman) {
        this.bow = bow;
        this.stickman = stickman;
        arrow = null;
        currentScore = null;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();

        g2d.translate(stickman.getPozX() - stickman.getStickman().getWidth() / 2, stickman.getPozY() - stickman.getStickman().getHeight() / 2);
        g2d.drawImage(stickman.getStickman(), 0, 0, null);
        g2d.setTransform(reset);

        g2d.translate(bow.getPozX() - bow.getAktualis().getWidth() / 2, bow.getPozY());
        g2d.rotate(bow.getRotAngle());
        g2d.drawImage(bow.getAktualis(), 0, -bow.getAktualis().getHeight() / 2, null);
        g2d.setTransform(reset);

        g2d.translate(stickman.getPozX() - 23, stickman.getPozY() - 28);
        g2d.rotate(bow.getRotAngle());
        g2d.setColor(Color.BLACK);
        g2d.fillRect(-8,-7,stickman.getArmLength() - (int)(15*sin(bow.getRotAngle())),14);
        g2d.setTransform(reset);

        //kez nelkuli stickman aminek rajzolok egy fekete vonalt keznek

        if (arrow != null) {
            g2d.translate(arrow.getPozX(), arrow.getPozY());
            g2d.rotate(arrow.getAktualisSzog());
            g2d.drawImage(arrow.getKep(), -arrow.getKep().getWidth() / 2, -arrow.getKep().getHeight() / 2, null);
            g2d.setTransform(reset);
        }

        if (currentScore != null) {
            g2d.setFont(new Font("Arial", Font.BOLD, 72));
            int offx = g2d.getFontMetrics().stringWidth(currentScore.getScore() + "") / 2;
            g2d.drawString(currentScore.getScore() + "", getWidth() / 2 - offx, getHeight() - getHeight() / 4 * 3);
        }
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    public void setCurrentScore(CurrentScore cs) {
        currentScore = cs;
    }
}
