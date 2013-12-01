package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;

public class Level3 extends LevelMinScore {
    protected Level3(Game game) {
        super(game);
    }

    @Override
    protected int getMinScore() {
        return 1500;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = null;
    }
}
