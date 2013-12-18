package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Block.Finish;
import cz.sparko.Bugmaze.GameUpdateHandler;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Manager.GameManager;

public abstract class Level {
    protected Game game;
    protected Level nextLevel;
    protected Level(Game game) {
        this.game = game;
    }

    public void reachedNextBlock(Block activeBlock, GameUpdateHandler gameUpdateHandler) {
        if (activeBlock instanceof Finish)
            gameUpdateHandler.gameOver(true);
    }

    public abstract Block createRandomBlock(Coordinate coordinate);

    public abstract Block[] getLevelBlocks();
    public abstract float getSpeed();
    public abstract void cleanLevelForStart();

    protected void initNextLevel() {
        this.nextLevel = null;
    }

    public boolean hasNextLevel() {
        if (nextLevel == null)
            initNextLevel();
        return nextLevel != null;
    }

    public Level getNextLevel() {
        if (nextLevel == null)
            initNextLevel();
        return nextLevel;
    }


}
