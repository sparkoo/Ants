package cz.sparko.Database;

import java.util.ArrayList;

public interface Schema {
    public String getCreateQuery();
    public ArrayList<String> getUpdateQuery();
    public String getName();
    public int getVersion();
}
