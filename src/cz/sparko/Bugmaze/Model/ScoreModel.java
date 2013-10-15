package cz.sparko.Bugmaze.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.sparko.Database.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO: better handling DB. Make just ScoreModel without ScoreDTO! insert/update/delete make static
public class ScoreModel {
    private static final String TABLE_NAME = "score";
    private static final String[] COLUMN_NAMES = {"_id", "score", "timestamp"};

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ScoreModel(Context context) {
        dbHelper = new DBHelper(context, Schema.getInstance());

    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ScoreDTO insertScore(ScoreDTO score) {
        ContentValues values = new ContentValues();
        String[] strValues = score.getStringValues();
        for (int i = 1; i < strValues.length; i++)
            values.put(COLUMN_NAMES[i], strValues[i]);
        long insertId = db.insert(TABLE_NAME, null, values);
        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, COLUMN_NAMES[0] + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ScoreDTO newScore = cursorToScore(cursor);
        cursor.close();
        return newScore;
    }

    public List<ScoreDTO> getAllScores() {
        List<ScoreDTO> scores = new ArrayList<ScoreDTO>();

        Cursor cursor = db.query(TABLE_NAME, COLUMN_NAMES, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            scores.add(cursorToScore(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        Collections.sort(scores);
        return scores;
    }

    public void deleteScore(ScoreDTO score) {
        long id = score.getId();
        System.out.println("Score deleted with id: " + id);
        db.delete(TABLE_NAME, COLUMN_NAMES[0] + " = " + id, null);
    }

    private ScoreDTO cursorToScore(Cursor cursor) {
        return new ScoreDTO(cursor.getLong(0), cursor.getLong(1), cursor.getString(2));
    }
}
