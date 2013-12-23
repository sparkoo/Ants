package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level3 extends LevelMinScore {
    public Level3(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, LineOneWay.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.70f, 0.15f, 0.05f, 0.1f};
    }

    @Override
    public float getSpeed() {
        return 1.3f;
    }

    @Override
    public int getTargetScore() {
        return 300;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level4(game);
    }
}
