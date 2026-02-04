package view;

import control.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// MINDENT MEGOLDO OTLET:
// Menuben majd egy high scores menupont, ami egy high_scores.txt-bol olvassa ki az eddigi high score-okat, amit
// stream-ekkel feldolgozva olvasol majd be es irsz is ki
//      -> fileolvasas pipa
//      -> stream-ek pipa
//      -> ertelmes menu pipa


public class GameFrame extends JFrame {
    public GameFrame(){
        this.setBounds(100,100,500,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));

        CardLayout layout = new CardLayout();
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(layout);
        this.add(cardPanel);

        GameOverPanel gameOver = new GameOverPanel(0,layout,cardPanel);
        cardPanel.add(gameOver,"GameOver");

        Map<String,GameScreenPanel> cards = HashMap.newHashMap(2);
        Map<String,ScorePanel> scoreMap = HashMap.newHashMap(1);

        MenuPanel menuPanel = new MenuPanel(layout,cardPanel,cards,gameOver,scoreMap);
        cardPanel.add(menuPanel,"Menu");

        layout.show(cardPanel,"Menu");

        AudioPlayer hangLejatszo = new AudioPlayer();
        hangLejatszo.playMusic();

        this.setVisible(true);
    }
}
