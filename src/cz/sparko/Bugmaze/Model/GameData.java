package cz.sparko.Bugmaze.Model;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.GameDataEnum;

import java.io.*;

public class GameData implements Serializable, Comparable {
    private long timestamp = 0;

    private int coins;

    public GameData(long timestamp, int coins) {
        this.coins = coins;
    }

    public GameData() {}

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public byte[] getByteStream() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(this);
        return b.toByteArray();
    }

    public static GameData getGameDataFromByteStream(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(data);
        ObjectInputStream o = new ObjectInputStream(b);
        return (GameData)o.readObject();
    }

    public static GameData getGameDataFromSharedPreferences(Game game) {
        GameData gameData = new GameData(game.getGameDataInt(GameDataEnum.TIMESTAMP), game.getGameDataInt(GameDataEnum.COINS));
        return gameData;
    }

    @Override
    public int compareTo(Object o) {
        GameData that = (GameData)o;
        if (timestamp > that.getTimestamp()) return -1;
        else if (timestamp < that.getTimestamp()) return 1;
        else return 0;
    }
}
