package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;

public class Level2 extends LevelMinScore {
    protected Level2(Game game) {
        super(game);
    }

    @Override
    protected int getMinScore() {
        return 1000;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level3(game);
    }
}
