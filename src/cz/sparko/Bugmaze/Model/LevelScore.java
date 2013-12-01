package cz.sparko.Bugmaze.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LevelScore extends Model {
    private static final String TABLE_NAME = "LevelScore";
    private static final String[] COLUMN_NAMES = {"classname", "highScore", "completed"};

    private String levelName;
    private long highScore;
    private int completed;

    public LevelScore(String levelName, long highScore, int completed) {
        super(TABLE_NAME, COLUMN_NAMES);
        this.levelName = levelName;
        this.highScore = highScore;
        this.completed = completed;
    }

    public LevelScore(String levelName) {
        super(TABLE_NAME, COLUMN_NAMES);
        this.levelName = levelName;
        this.highScore = 0;
        this.completed = 0;
    }

    public String getLevelName() { return levelName; }
    public int getCompleted() { return completed; }
    public long getHighScore() { return highScore; }
    public boolean isCompleted() { return completed == 1; }

    public static void updateProgress() {

    }

    public static LevelScore insertLevelScore(SQLiteDatabase db, String levelName) {
        return insertLevelScore(db, new LevelScore(levelName));
    }

    public static LevelScore insertLevelScore(SQLiteDatabase db, LevelScore levelScore) {
        Cursor cursor = Model.insert(TABLE_NAME, COLUMN_NAMES, levelScore.getStringValues(), db);
        LevelScore newLevelScore = cursorToModel(cursor);
        cursor.close();
        return newLevelScore;
    }

    @Override
    public String[] getStringValues() {
        String[] strValues = {levelName, ((Long)highScore).toString(), ((Integer)completed).toString()};
        return strValues;
    }

    private static LevelScore cursorToModel(Cursor cursor) {
        return new LevelScore(cursor.getString(0), cursor.getLong(1), cursor.getInt(2));
    }
}
