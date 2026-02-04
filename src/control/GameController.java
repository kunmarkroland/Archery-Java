package control;

import model.Arrow;
import model.Bow;
import model.CurrentScore;
import model.Stickman;
import view.GamePanel;
import view.GameScreenPanel;
import view.Target;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.*;
import static java.lang.System.nanoTime;

public class GameController extends MouseAdapter {
    private Arrow arrow;
    private Bow bow;
    private Target target;
    private Stickman stickman;
    private GameScreenPanel gameScreenPanel;
    private GamePanel panel;
    private GameRule gameRule;
    private boolean arrowAlive;
    private AudioPlayer audioPlayer;

    private long startTime;
    private double t;

    private static final double G = -9.81;
    private static final double VMAX = 500;

    public GameController(GameScreenPanel gameScreenPanel, Bow bow, Target target, Stickman stickman) {
        this.gameScreenPanel = gameScreenPanel;
        panel = this.gameScreenPanel.getGamePanel();
        gameRule = this.gameScreenPanel.getGameRule();
        this.bow = bow;
        this.target = target;
        this.stickman = stickman;
        arrow = null;
        arrowAlive = false;

        audioPlayer = new AudioPlayer();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        startTime = nanoTime();
        bow.toFullDraw();
        stickman.setLongArm();

        gameScreenPanel.update();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        double timeDifference = ((double) nanoTime() - (double) startTime) / 1000000;
        bow.toAlaphelyzet();
        stickman.setShortArm();
        if (arrowAlive) {
            gameScreenPanel.update();
        } else if (!gameRule.gameOver()) {
            System.out.println("Arrow alive!");
            arrowAlive = true;
            gameScreenPanel.setArrowAlive(true);
            audioPlayer.playShotSound();

            gameRule.takeArrow();   //csokkentem a megmaradt nyilvesszok szamat

            double szog = atan2(bow.getPozY() - e.getY(), e.getX() - bow.getPozX() + bow.getAktualis().getWidth() / 2); // ez a sor passzol az arrow vy-t beallito azon sorral, amely (- v * sin())-t allit be, hogy korrigalja a JPanel Y iranyanak lefele novekedeset. Ez az oka annak is, hogy G negativ
            //double szog = atan2(e.getY() - bow.getPozY(), e.getX() - bow.getPozX() + bow.getAktualis().getWidth() / 2);   //bow.getAktualis().getWidth()/2
            double v = timeDifference < 600 ? VMAX * timeDifference / 1000 : VMAX;                                           //az nyilvesszo nokkja-tol merje a szoget, he az ij kozepetol
            int bowX = bow.getPozX();
            int xc = bowX - bow.getAktualis().getWidth() / 2;// forgaspont, ami korul elfordult az ij
            int bowY = bow.getPozY();
            int arrowCenterX = (int) (cos(szog) * (bowX - xc) + xc);    // (xc,yc) pont koruli elforgatas
            int arrowCenterY = (int) (-sin(szog) * (bowX - xc) + bowY);
            arrow = new Arrow(szog, v, arrowCenterX, arrowCenterY);

            panel.setArrow(arrow);
            t = 0; //eltelt ido: t jeloles, hogy talaljon a kepletekkel pl wikipediarol

            Timer timer = new Timer(16, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    t += 0.1;
                    // nyilvesszo x es y koordinatajanak es sebessegenek beallitasa

                    int x = arrow.getEredetiX() + (int) (arrow.getVx() * t);
                    int y = arrow.getEredetiY() + (int) (arrow.getVy() * t - G / 2 * t * t);

                    double vy = arrow.getVy() - G * t;

                    double aktszog = atan2(vy, arrow.getVx());

                    int kephosszfele = arrow.getKep().getWidth() / 2;
                    int tipX = (int) (kephosszfele * cos(aktszog)) + x;
                    int tipY = (int) (kephosszfele * sin(aktszog)) + y;

                    boolean folytatni = true;

                    int targetWidth = target.getWidth();
                    int targetHeight = target.getHeight();
                    int targetBalFelsoSarokX = target.getPozX();
                    int targetBalFelsoSarokY = target.getPozY();
                    double metszesX = (double) (targetBalFelsoSarokY + targetHeight - arrow.getRegiY()) * (tipX - arrow.getRegiX()) / (tipY - arrow.getRegiY()) + arrow.getRegiX();

                    if ((tipX >= targetBalFelsoSarokX && arrow.getRegiX() <= targetBalFelsoSarokX && tipY >= targetBalFelsoSarokY && tipY <= targetBalFelsoSarokY + targetHeight) ||
                            (tipX >= targetBalFelsoSarokX && arrow.getRegiX() <= targetBalFelsoSarokX && tipY >= targetBalFelsoSarokY && arrow.getRegiY() <= targetBalFelsoSarokY) ||
                            (tipX >= targetBalFelsoSarokX && tipX <= targetBalFelsoSarokX + targetWidth && tipY >= targetBalFelsoSarokY && arrow.getRegiY() <= targetBalFelsoSarokY) ||
                            (tipX >= targetBalFelsoSarokX && arrow.getRegiX() <= targetBalFelsoSarokX && tipY >= targetBalFelsoSarokY + targetHeight && arrow.getRegiY() <= targetBalFelsoSarokY + targetHeight && metszesX >= targetBalFelsoSarokX && metszesX <= targetBalFelsoSarokX + targetWidth)) {
                        System.out.println("Eltalalta a celt!");

                        folytatni = false;
                        audioPlayer.playHitSound();

                        int score;

                        double tipXTmp, tipYTmp;
                        if (tipX == arrow.getRegiX()) {
                            tipXTmp = tipX;
                            tipYTmp = targetBalFelsoSarokY;
                        } else if (tipY == arrow.getRegiY()) {
                            tipXTmp = targetBalFelsoSarokX;
                            tipYTmp = tipY;
                        } else if ((tipX >= targetBalFelsoSarokX && arrow.getRegiX() <= targetBalFelsoSarokX && tipY >= targetBalFelsoSarokY && tipY <= targetBalFelsoSarokY + targetHeight) ||
                                (tipX >= targetBalFelsoSarokX && arrow.getRegiX() <= targetBalFelsoSarokX && tipY >= targetBalFelsoSarokY + targetHeight && arrow.getRegiY() <= targetBalFelsoSarokY + targetHeight && metszesX >= targetBalFelsoSarokX && metszesX <= targetBalFelsoSarokX + targetWidth)) {
                            tipYTmp = (double) (targetBalFelsoSarokX - arrow.getRegiX()) * (double) (tipY - arrow.getRegiY()) / (double) (tipX - arrow.getRegiX()) + arrow.getRegiY();
                            tipXTmp = targetBalFelsoSarokX;
                        } else {
                            tipYTmp = targetBalFelsoSarokY;
                            tipXTmp = (double) (targetBalFelsoSarokY - arrow.getRegiY()) * (double) (tipX - arrow.getRegiX()) / (double) (tipY - arrow.getRegiY()) + arrow.getRegiX();
                        }

                        int hper5 = targetHeight / 5;
                        if ((ceil(tipYTmp) >= targetBalFelsoSarokY && tipYTmp < targetBalFelsoSarokY + hper5) || (tipYTmp >= targetBalFelsoSarokY + 4 * hper5 && tipYTmp <= targetBalFelsoSarokY + targetHeight) || (tipYTmp >= targetBalFelsoSarokY + targetHeight && arrow.getRegiY() <= targetBalFelsoSarokY + targetHeight)) {
                            score = 1;
                        } else {
                            if ((tipYTmp >= targetBalFelsoSarokY + hper5 && tipYTmp < targetBalFelsoSarokY + 2 * hper5) || (tipYTmp >= targetBalFelsoSarokY + 3 * hper5 && tipYTmp < targetBalFelsoSarokY + 4 * hper5)) {
                                score = 3;
                            } else {
                                score = 5;
                            }
                        }

                        double pierce = sqrt(arrow.getVx() * arrow.getVx() + vy * vy) / VMAX;
                        tipYTmp += pierce * arrow.getTipLength() * sin(aktszog);
                        tipXTmp += pierce * arrow.getTipLength() * cos(aktszog);

                        x = (int) (tipXTmp - kephosszfele * cos(aktszog));
                        y = (int) (tipYTmp - kephosszfele * sin(aktszog));

                        CurrentScore cs = new CurrentScore(score);
                        panel.setCurrentScore(cs);
                        gameRule.incrementScore(cs);
                    } else if (tipX < 0 || tipX > panel.getWidth() || tipY < 0 || tipY > panel.getHeight()) {
                        folytatni = false;
                        audioPlayer.playHitSound();
                        CurrentScore cs = new CurrentScore(0);
                        panel.setCurrentScore(cs);
                    }

                    arrow.setRegiX(tipX);
                    arrow.setRegiY(tipY);
                    arrow.setPozX(x);
                    arrow.setPozY(y);
                    arrow.setAktualisSzog(aktszog);

                    gameScreenPanel.update();   // itt arrowAlive meg true

                    if (!folytatni) {
                        ((Timer) e.getSource()).stop();
                        Timer delay = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                arrowAlive = false;
                                gameScreenPanel.setArrowAlive(false);
                                panel.setArrow(null);
                                System.out.println("Arrow dead");
                                panel.setCurrentScore(null);
                                ((Timer) e.getSource()).stop();
                                gameScreenPanel.update();   //itt mar arrowAlive false
                            }
                        });
                        delay.start();
                    }
                }
            });
            timer.start();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        double szog = atan2(e.getY() - bow.getPozY(), e.getX() - bow.getPozX() + bow.getAktualis().getWidth() / 2);
        bow.setRotAngle(szog);
        gameScreenPanel.update();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        double szog = atan2(e.getY() - bow.getPozY(), e.getX() - bow.getPozX() + bow.getAktualis().getWidth() / 2);
        bow.setRotAngle(szog);
        gameScreenPanel.update();
    }
}
