package cz.sparko.Ants.Models;

import java.util.ArrayList;

public class Schema implements cz.sparko.Database.Schema {
    private static Schema instance = null;

    private ArrayList<String> updateQuery = new ArrayList<String>();
    private String createQuery;

    private Schema(){}

    public static Schema getInstance() {
        if (instance == null)
            instance = new Schema();
        return instance;
    }

    @Override
    public String getCreateQuery() {
        //TODO: read from file assets/db/create.sql
        createQuery = "CREATE TABLE [score] ([_id] INTEGER NOT NULL PRIMARY KEY, [score] INTEGER NOT NULL, [timestamp] TIMESTAMP NOT NULL);";
        return createQuery;
    }

    @Override
    public ArrayList<String> getUpdateQuery() {
        //TODO: read from file assets/db/update.sql
        return updateQuery;
    }

    @Override
    public String getName() {
        //TODO: read from file assets/db/NAME
        return "Ants";
    }

    @Override
    public int getVersion() {
        //TODO: read from file assets/db/VERSION
        return 1;
    }
}
