package cz.sparko.Bugmaze.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Model {
    private String table;
    private String[] columns;

    public Model(String table, String[] columns) {
        this.table = table;
        this.columns = columns;
    }

    public abstract String[] getStringValues();

    public String getColumnName(int index) { return columns[index]; }
    public String[] getColumnNames() { return columns; }
    public String getTableName() { return table; }

    protected static Cursor insert(String tableName, String[] columnNames, String[] strValues, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        for (int i = 1; i < strValues.length; i++)
            values.put(columnNames[i], strValues[i]);
        long insertId = db.insert(tableName, null, values);
        Cursor cursor = db.query(tableName, columnNames, columnNames[0] + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}
