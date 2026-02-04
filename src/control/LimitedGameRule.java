package control;

import model.CurrentScore;

public class LimitedGameRule implements GameRule{
    private int arrowsLeft, score;

    public LimitedGameRule() {
        this.arrowsLeft = 5;
        this.score = 0;
    }

    @Override
    public int getArrowsLeft() {
        return arrowsLeft;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void takeArrow() {
        arrowsLeft--;
    }

    @Override
    public void incrementScore(CurrentScore cs) {
        score+=cs.getScore();
    }

    @Override
    public boolean gameOver() {
        return arrowsLeft<=0;
    }
}
