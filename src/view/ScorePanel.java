package view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScorePanel extends JPanel {
    private void addJLabel(String text, JPanel panel){
        JLabel egyScore = new JLabel("",SwingConstants.CENTER);
        egyScore.setFont(new Font("Arial", Font.BOLD, 30));
        egyScore.setText(text);
        egyScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(egyScore);
    }

    public ScorePanel(JButton menuButton){
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel scoresText = new JLabel("",SwingConstants.CENTER);
        scoresText.setFont(new Font("Arial", Font.BOLD, 50));
        scoresText.setText("High Scores\n");
        scoresText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scoresText);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(menuButton);
        addJLabel(" ",panel);

        try {
            BufferedReader br = new BufferedReader(new FileReader("scores/score.txt"));
            br.lines().forEach(line->addJLabel(line,panel));
            br.close();
        } catch (IOException ioe) {
            System.out.println("Hiba a score.txt file beolvasasanal");
        }

        this.add(scrollPane);
    }
}
