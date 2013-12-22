package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level10 extends LevelMinScore {
    public Level10(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CornerMine.class, LineMine.class, LineOneWay.class, CornerNoRotate.class, LineNoRotate.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.62f, 0.07f, 0.1f, 0.05f, 0.05f, 0.05f, 0.03f, 0.03f};
    }

    @Override
    public float getSpeed() {
        return 0.9f;
    }

    @Override
    public int getTargetScore() {
        return 1600;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level11(game);
    }
}
