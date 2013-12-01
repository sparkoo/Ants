package cz.sparko.Bugmaze.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score extends Model implements Comparable{
    private static final String TABLE_NAME = "Score";
    private static final String[] COLUMN_NAMES = {"_id", "score", "timestamp"};

    private long id;
    private long score;
    private String timestamp;

    public Score(long id, long score, String timestamp) {
        super(TABLE_NAME, COLUMN_NAMES);
        this.id = id;
        this.score = score;
        this.timestamp = timestamp;
    }

    public Score(long score, String timestamp) {
        super(TABLE_NAME, COLUMN_NAMES);
        this.score = score;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public long getScore() { return score; }
    public String getTimestamp() { return timestamp; }

    public String[] getStringValues() {
        String[] strValues = {((Long)id).toString(), ((Long)score).toString(), timestamp.toString()};
        return strValues;
    }

    @Override
    public int compareTo(Object o) {
        Score that = (Score)o;
        if (score > that.getScore()) return -1;
        if (score < that.getScore()) return 1;
        return 0;
    }

    public static Score insertScore(Score score, SQLiteDatabase db) {
        Cursor cursor = Model.insert(TABLE_NAME, COLUMN_NAMES, score.getStringValues(), db);
        Score newScore = cursorToModel(cursor);
        cursor.close();
        return newScore;
    }

    public static List<Score> getAllScores(SQLiteDatabase db) {
        List<Score> scores = new ArrayList<Score>();

        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            scores.add(cursorToModel(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        Collections.sort(scores);
        return scores;
    }

    public static void deleteScore(Score score, SQLiteDatabase db) {
        long id = score.getId();
        System.out.println("Score deleted with id: " + id);
        db.delete(TABLE_NAME, COLUMN_NAMES[0] + " = " + id, null);
    }

    private static Score cursorToModel(Cursor cursor) {
        return new Score(cursor.getLong(0), cursor.getLong(1), cursor.getString(2));
    }
}
