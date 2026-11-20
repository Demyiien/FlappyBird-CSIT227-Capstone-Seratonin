package flapibirb.model;

public class Scoreboard {

    private int counter;

    public int getCounter() {
        return counter;
    }

    public void increment() {
        counter++;
    }

    public void reset() {
        counter = 0;
    }

    public String getScoreText() {
        return String.valueOf(counter);
    }
}
