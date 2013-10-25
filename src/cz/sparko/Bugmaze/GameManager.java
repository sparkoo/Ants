package cz.sparko.Bugmaze;

public class GameManager extends Manager {
    private static GameManager instance;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance != null)
            return instance;
        instance = new GameManager();
        return instance;
    }
}
