package cz.sparko.Bugmaze.Model;

import android.content.Context;
import cz.sparko.Bugmaze.Helper.AssetFileReader;

import java.util.ArrayList;

public class Schema implements cz.sparko.Database.Schema {
    private final Context context;

    public Schema(Context context){
        this.context = context;
    }

    @Override
    public ArrayList<String> getCreateQuery() {
        return AssetFileReader.fileToList(context.getAssets(), "db/create.sql");
    }

    @Override
    public ArrayList<String> getUpdateQuery() {
        return AssetFileReader.fileToList(context.getAssets(), "db/update.sql");
    }

    @Override
    public String getName() {
        return AssetFileReader.fileToString(context.getAssets(), "db/NAME");
    }

    @Override
    public int getVersion() {
        return Integer.parseInt(AssetFileReader.fileToString(context.getAssets(), "db/VERSION"));
    }
}
