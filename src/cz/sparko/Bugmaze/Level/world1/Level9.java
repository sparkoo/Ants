package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level9 extends LevelMinScore {
    public Level9(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 0.7f;
    }

    @Override
    protected int getMinScore() {
        return 1500;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level10(game);
    }
}
