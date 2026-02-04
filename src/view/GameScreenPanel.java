package view;

import control.GameController;
import control.GameRule;
import model.Bow;
import model.Stickman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static java.lang.Math.min;

public class GameScreenPanel extends JPanel {
    private GamePanel gamePanel;
    private HUDPanel hud;
    private GameRule gameRule;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private GameOverPanel gameOverPanel;

    private boolean arrowAlive;

    public GameScreenPanel(GameRule gameRule, JButton menu, CardLayout layout, JPanel cardPanel, GameOverPanel gameOverPanel) {
        this.setLayout(new BorderLayout());
        this.gameRule = gameRule;

        cardLayout=layout;
        this.cardPanel=cardPanel;
        this.gameOverPanel=gameOverPanel;

        arrowAlive = false;

        Bow bow = new Bow(0, 0);
        Stickman stickman = new Stickman(0, 0);

        gamePanel = new GamePanel(bow, stickman);
        Target target = new Target(0, 0);
        gamePanel.add(target);
        gamePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = min(gamePanel.getWidth() * 5 / 100, 30);
                int h = gamePanel.getHeight() / 3;
                int x = gamePanel.getWidth() * 9 / 10;
                int y = gamePanel.getHeight() * 9 / 10 - h;
                target.setBounds(x, y, w, h);
                target.setPozX(x);
                target.setPozY(y);
                bow.setPozX(gamePanel.getWidth() / 10 + 150 / 2); //a 150 a 2 ijallapot kozul a wide-abb width-je
                bow.setPozY(y);

                stickman.setPozX(bow.getPozX() - bow.getAktualis().getWidth() / 2 + 30);
                stickman.setPozY(y + 43);
            }
        });

        GameController controller = new GameController(this, bow, target, stickman);
        gamePanel.addMouseListener(controller);
        gamePanel.addMouseMotionListener(controller);
        this.add(gamePanel);

        hud = new HUDPanel(gameRule, menu);
        this.add(hud, BorderLayout.NORTH);
    }

    public GameRule getGameRule() {
        return gameRule;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setArrowAlive(boolean arrowState) {
        this.arrowAlive = arrowState;
    }

    public void update() {
        gamePanel.repaint();
        hud.repaint();
        if(gameRule.gameOver() && !arrowAlive) {
            System.out.println("Game Over");
            //game over logika kezelese
            gameOverPanel.setScore(gameRule.getScore());
            cardPanel.revalidate();
            cardPanel.repaint();
            cardLayout.show(cardPanel,"GameOver");
        }
    }
}
