package cz.sparko.Bugmaze.Model;

import android.database.sqlite.SQLiteDatabase;

public class LevelScore {
    private static final String TABLE_NAME = "LevelScore";
    private static final String[] COLUMN_NAMES = {"classname", "highScore", "completed"};

    private String levelName;
    private long highScore;
    private int completed;

    public LevelScore(String levelName, long highScore, int completed) {
        this.levelName = levelName;
        this.highScore = highScore;
        this.completed = completed;
    }

    public String getLevelName() { return levelName; }
    public int getCompleted() { return completed; }
    public long getHighScore() { return highScore; }
    public boolean isCompleted() { return completed == 1; }

    public static void updateProgress() {

    }

    public static void insertEmptyProgress(SQLiteDatabase db) {

    }
}
