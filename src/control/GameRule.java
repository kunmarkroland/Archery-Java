package control;

import model.CurrentScore;

public interface GameRule {
    public int getArrowsLeft();
    public int getScore();
    public void takeArrow();
    public void incrementScore(CurrentScore cs);
    public boolean gameOver();
}
