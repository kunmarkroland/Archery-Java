package view;

import control.LimitedGameRule;
import control.PracticeGameRule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class MenuPanel extends JPanel {
    private JLabel menu;
    private JButton practice, limited, scores;

    public MenuPanel(CardLayout layout, JPanel cardPanel, Map<String, GameScreenPanel> cards, GameOverPanel gameOverPanel, Map<String, ScorePanel> scoreMap) {
        this.setLayout(new GridLayout(3, 1));

        menu = new JLabel("", SwingConstants.CENTER);
        menu.setFont(new Font("Arial", Font.BOLD, 72));
        menu.setText("Menu");

        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(cardPanel, "Menu");
            }
        });

        practice = new JButton("Practice Game");

        practice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameScreenPanel oldPanel = cards.get("Practice");
                if (oldPanel != null) {
                    cardPanel.remove(oldPanel);
                    cards.remove("Practice");
                }
                GameScreenPanel practicePanel = new GameScreenPanel(new PracticeGameRule(), menuButton, layout, cardPanel, gameOverPanel);
                cardPanel.add(practicePanel, "Practice");
                cards.put("Practice", practicePanel);
                cardPanel.revalidate();
                cardPanel.repaint();
                layout.show(cardPanel, "Practice");
            }
        });

        limited = new JButton("Limited Shot Game");

        limited.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameScreenPanel oldPanel = cards.get("Limited");
                if (oldPanel != null) {
                    cardPanel.remove(oldPanel);
                    cards.remove("Limited");
                }
                GameScreenPanel limitedPanel = new GameScreenPanel(new LimitedGameRule(), menuButton, layout, cardPanel, gameOverPanel);
                cardPanel.add(limitedPanel, "Limited");
                cards.put("Limited", limitedPanel);
                cardPanel.revalidate();
                cardPanel.repaint();
                layout.show(cardPanel, "Limited");
            }
        });

        scores = new JButton("Scores");
        scores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScorePanel oldScorePanel = scoreMap.get("Score");
                if (oldScorePanel != null) {
                    cardPanel.remove(oldScorePanel);
                    scoreMap.remove("Score");
                }
                ScorePanel scorePanel = new ScorePanel(menuButton);
                cardPanel.add(scorePanel, "Score");
                scoreMap.put("Score", scorePanel);
                cardPanel.revalidate();
                cardPanel.repaint();
                layout.show(cardPanel, "Score");
            }
        });

        JPanel tmp = new JPanel();
        JPanel tmp2 = new JPanel();

        practice.setPreferredSize(new Dimension(150,40));
        limited.setPreferredSize(new Dimension(150,40));

        tmp.add(practice);
        tmp.add(limited);

        scores.setPreferredSize(new Dimension(100,40));
        tmp2.add(scores);

        this.add(menu);
        this.add(tmp);
        this.add(tmp2);
    }
}
