package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level5 extends LevelMinScore {
    public Level5(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, LineOneWay.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.68f, 0.17f, 0.03f, 0.03f, 0.09f};
    }

    @Override
    public float getSpeed() {
        return 1.1f;
    }

    @Override
    public int getTargetScore() {
        return 500;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level6(game);
    }
}
