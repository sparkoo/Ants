package cz.sparko.Ants.Models;

public class ScoreDTO implements Comparable {
    private long id;
    private long score;
    private String timestamp;

    public ScoreDTO(long id, long score, String timestamp) {
        this.id = id;
        this.score = score;
        this.timestamp = timestamp;
    }

    public ScoreDTO(long score, String timestamp) {
        this.score = score;
        this.timestamp = timestamp;
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String[] getStringValues() {
        String[] strValues = {((Long)id).toString(), ((Long)score).toString(), timestamp.toString()};
        return strValues;
    }

    @Override
    public int compareTo(Object o) {
        ScoreDTO that = (ScoreDTO)o;
        if (score > that.getScore()) return -1;
        if (score < that.getScore()) return 1;
        return 0;
    }
}
