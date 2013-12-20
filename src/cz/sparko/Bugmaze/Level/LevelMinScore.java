package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.GameField;
import cz.sparko.Bugmaze.GameUpdateHandler;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Manager.GameManager;

import java.util.Random;

public abstract class LevelMinScore extends Level {
    private final int targetScore;
    private boolean hasFinishBlock = false;

    protected LevelMinScore(Game game) {
        super(game);
        targetScore = getTargetScore();
    }

    protected LevelMinScore() {
        super();
        targetScore = getTargetScore();
    }

    public abstract int getTargetScore();

    @Override
    public void reachedNextBlock(Block activeBlock, GameUpdateHandler gameUpdateHandler) {
        super.reachedNextBlock(activeBlock, gameUpdateHandler);
    }

    @Override
    public Block[] getLevelBlocks() {
        if (!hasFinishBlock && GameManager.getInstance().getScore() > targetScore) {
            hasFinishBlock = true;
            Block[] levelBlocks = new Block[1];
            Random rnd = new Random();
            Coordinate activeBlockCoordinate = GameManager.getGameField().getActiveBlock().getCoordinate();
            Coordinate finishBlockCoordinate = new Coordinate(rnd.nextInt(GameField.FIELD_SIZE_X), rnd.nextInt(GameField.FIELD_SIZE_Y));
            while (activeBlockCoordinate.equals(finishBlockCoordinate))
                finishBlockCoordinate = new Coordinate(rnd.nextInt(GameField.FIELD_SIZE_X), rnd.nextInt(GameField.FIELD_SIZE_Y));
            levelBlocks[0] = new Finish(finishBlockCoordinate, game);
            return levelBlocks;
        }
        return new Block[0];
    }

    @Override
    public void cleanLevelForStart() {
        hasFinishBlock = false;
    }
}
