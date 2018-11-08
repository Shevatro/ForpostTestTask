package ua.forposttest.data.model;

import java.util.List;

public class FighterRec {

    private List<Fighter> fighters;
    private long timeBetweenSteps;

    public FighterRec(List<Fighter> fighters, long timeBetweenSteps) {
        this.fighters = fighters;
        this.timeBetweenSteps = timeBetweenSteps;
    }

    public List<Fighter> getFighters() {
        return fighters;
    }

    public long getTimeBetweenSteps() {
        return timeBetweenSteps;
    }

    public void setFighters(List<Fighter> fighters) {
        this.fighters = fighters;
    }

    public void setTimeBetweenSteps(long timeBetweenSteps) {
        this.timeBetweenSteps = timeBetweenSteps;
    }

    @Override
    public String toString() {
        return "FighterRec{" +
                "fighters=" + fighters.toString() +
                ", timeBetweenSteps=" + timeBetweenSteps +
                '}';
    }
}
