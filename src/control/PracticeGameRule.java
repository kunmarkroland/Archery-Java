package control;

import model.CurrentScore;

public class PracticeGameRule implements GameRule{
    private int arrowsLeft, score;

    public PracticeGameRule() {
        this.arrowsLeft = -1;
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

    }

    @Override
    public void incrementScore(CurrentScore cs) {
        score+=cs.getScore();
    }

    @Override
    public boolean gameOver() {
        return false;
    }
}
