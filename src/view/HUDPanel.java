package view;

import control.GameRule;

import javax.swing.*;
import java.awt.*;

public class HUDPanel extends JPanel {
    private GameRule szabaly;
    private JLabel arrowsL,score;
    private JButton menu;

    public HUDPanel(GameRule szabaly, JButton menu) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER,25,10));
        this.szabaly = szabaly;
        this.menu = menu;
        arrowsL = new JLabel("");
        score = new JLabel("");

        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(arrowsL);
        this.add(score);
        this.add(this.menu);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        arrowsL.setText("➵ left: " + (szabaly.getArrowsLeft() >= 0 ? ("" + szabaly.getArrowsLeft()) : "∞"));
        //arrowsL.setFont(new Font("Arial", Font.BOLD, 20));
        score.setText("Score: " + szabaly.getScore());
        //score.setFont(new Font("Arial", Font.BOLD, 20));
    }
}
