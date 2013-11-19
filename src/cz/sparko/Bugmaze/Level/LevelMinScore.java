package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.GameUpdateHandler;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Manager.GameManager;

public abstract class LevelMinScore extends Level {
    private final int minScore;
    public LevelMinScore(Game game) {
        super(game);
        minScore = getMinScore();
    }

    protected abstract int getMinScore();

    @Override
    public void reachedNextBlock(Block activeBlock, GameUpdateHandler gameUpdateHandler) {
        super.reachedNextBlock(activeBlock, gameUpdateHandler);
    }

    @Override
    public Block createRandomBlock(Coordinate coordinate) {
        Class[] blocks = {Corner.class, Line.class, Cross.class};
        float[] probabilities = {0.7f, 0.2f, 0.1f};
        int[] walkThroughs = {1, 1, 2};
        try {
            return Block.createRandomBlock(blocks, probabilities, walkThroughs, game, coordinate, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Block[] getLevelBlocks() {
        if (GameManager.getInstance().getScore() > minScore) {
            Block[] levelBlocks = new Block[1];
            levelBlocks[0] = new Finish(new Coordinate(1, 1), game);
            return levelBlocks;
        }
        return new Block[0];
    }
}
