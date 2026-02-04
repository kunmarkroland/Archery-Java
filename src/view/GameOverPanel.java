package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameOverPanel extends JPanel {
    private JLabel gameOverLabel, scoreLabel;
    private JButton saveScoreButton, menuButton;
    private int score;

    public GameOverPanel(int sc, CardLayout layout, JPanel cardPanel) {
        this.setLayout(new GridLayout(3,1));
        this.score = sc;
        gameOverLabel = new JLabel("",SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 72));
        gameOverLabel.setText("Game Over!");
        scoreLabel = new JLabel("",SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 50));
        scoreLabel.setText("Your score is: " + score);

        saveScoreButton = new JButton("Save score");
        saveScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("scores/score.txt"));
                    List<Integer> lista;

                    lista = br.lines().map(Integer::parseInt).collect(Collectors.toList());
                    lista.add(score);
                    Collections.sort(lista, Comparator.reverseOrder());
                    if (lista.size() > 20) {
                        lista.removeLast();
                    }

                    try (FileWriter writer = new FileWriter("scores/score.txt")) {
                        writer.write(lista.stream().map(sc -> sc + "\n").reduce("", (ossz, akt) -> ossz + akt));
                        System.out.println("File elmentve");
                    } catch (IOException ex) {
                        System.out.println("Hiba a score.txt fileba valo irasnal");
                    }

                } catch (IOException ioe) {
                    System.out.println("Hiba a score.txt file beolvasasanal");
                }

                layout.show(cardPanel,"Menu");
            }
        });

        menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(cardPanel,"Menu");
            }
        });

        JPanel tmp = new JPanel();
        tmp.add(saveScoreButton);
        tmp.add(menuButton);

        this.add(gameOverLabel);
        this.add(scoreLabel);
        this.add(tmp);
    }

    public void setScore(int score){
        this.score = score;
        scoreLabel.setText("Your score is: " + score);
    }
}
