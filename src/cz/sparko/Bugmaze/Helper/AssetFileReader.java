package cz.sparko.Bugmaze.Helper;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AssetFileReader {
    public static String fileToString(AssetManager assetManager, String file) {
        StringBuilder createQuery = new StringBuilder();
        try {
            InputStream fstream = assetManager.open(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)
                createQuery.append(strLine);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createQuery.toString();
    }

    public static ArrayList<String> fileToList(AssetManager assetManager, String file) {
        ArrayList<String> updateQuery = new ArrayList<String>();
        try {
            InputStream fstream = assetManager.open(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)
                updateQuery.add(strLine);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateQuery;
    }
}
