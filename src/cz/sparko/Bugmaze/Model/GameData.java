package cz.sparko.Bugmaze.Model;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.GameDataEnum;

import java.io.*;
import java.util.Calendar;

public class GameData implements Serializable, Comparable {
    private static final long serialVersionUID = 42L;

    private long timestamp = 0;

    private int coins;

    public GameData(int coins) {
        this.coins = coins;
        timestamp = Calendar.getInstance().getTimeInMillis();
    }
    public GameData(long timestamp, int coins) {
        this.coins = coins;
        this.timestamp = timestamp;
    }

    public GameData() {}

    public int getCoins() { return coins; }
    public long getTimestamp() { return timestamp; }
    public void updateCoins(int plusCoins) {
        this.coins += plusCoins;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

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
        GameData gameData = new GameData(game.getGameDataTimestamp(), game.getGameDataInt(GameDataEnum.COINS));
        return gameData;
    }

    public void saveGameDataToSharedPreferences(Game game) {
        game.setGameData(GameDataEnum.COINS, this.coins);
        game.setGameData(GameDataEnum.TIMESTAMP, this.timestamp);
    }

    @Override
    public int compareTo(Object o) {
        GameData that = (GameData)o;
        if (timestamp > that.getTimestamp()) return 1;
        else if (timestamp < that.getTimestamp()) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return String.format("%d => %d", timestamp, coins);
    }
}
