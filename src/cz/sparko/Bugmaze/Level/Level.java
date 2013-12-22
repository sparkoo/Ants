package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.GameUpdateHandler;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Manager.GameManager;

public abstract class Level {
    protected Game game;
    protected Level nextLevel;
    protected Class[] blockTypes;
    protected float[] blockProbabilities;

    protected Level(Game game) {
        this.game = game;

        this.blockTypes = getBlockTypes();
        this.blockProbabilities = getBlockProbabilities();
    }

    protected Level() { }

    public void reachedNextBlock(Block activeBlock, GameUpdateHandler gameUpdateHandler) {
        if (activeBlock instanceof Finish)
            gameUpdateHandler.gameOver(true);
    }

    public Block createRandomBlock(Coordinate coordinate) {
        try {
            return Block.createRandomBlock(blockTypes, blockProbabilities, game, coordinate, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract Class[] getBlockTypes();
    protected abstract float[] getBlockProbabilities();

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

    public void onRefreshField() {
        for (Block blockline[] : GameManager.getGameField().getBlocks())
            for (Block block : blockline)
                if (block instanceof HasMine)
                    GameManager.getGameField().putBlock(block.getCoordinate().getX(), block.getCoordinate().getY(), ((HasMine) block).getUnminedBlock(game));
    }

    public boolean testLevel() {
        if (blockTypes.length != blockProbabilities.length)
            return false;

        float sum = 0;
        int sumInt;
        for (float increment : blockProbabilities)
            sum += increment;

        sumInt = java.lang.Math.round(sum*1000);
        if (sumInt != 1000) {
            System.out.println(sumInt);
            return false;
        }

        return true;
    }
}
