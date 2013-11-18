package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Helper.Coordinate;

public abstract class Level {
    protected Game game;
    protected Level(Game game) {
        this.game = game;
    }

    public abstract void reachedNextBlock();

    public abstract Block createRandomBlock(Coordinate coordinate);
}
