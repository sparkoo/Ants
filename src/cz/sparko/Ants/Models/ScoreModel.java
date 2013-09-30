package cz.sparko.Ants.Models;

import java.security.Timestamp;

public class ScoreModel {
    private long id;
    private long score;
    private Timestamp timestamp;

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
