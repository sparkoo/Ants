package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Block.Finish;
import cz.sparko.Bugmaze.Helper.Coordinate;

public class Level1 extends Level {
    public Level1(Game game) {
        super(game);
    }

    @Override
    public void reachedNextBlock() {
    }

    @Override
    public Block createRandomBlock(Coordinate coordinate) {
        return new Finish(coordinate, game, 1);
    }
}
