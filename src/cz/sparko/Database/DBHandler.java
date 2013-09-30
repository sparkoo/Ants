package cz.sparko.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private Schema schema;

    public DBHandler(Context context, Schema schema) {
        super(context, schema.getName(), null, schema.getVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(schema.getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        for (String sqlUpdateQuery : schema.getUpdateQuery())
            db.execSQL(sqlUpdateQuery);
    }
}
