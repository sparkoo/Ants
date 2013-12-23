package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level4 extends LevelMinScore {
    public Level4(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, LineOneWay.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.69f, 0.2f, 0.03f, 0.03f, 0.05f};
    }

    @Override
    public float getSpeed() {
        return 1.2f;
    }

    @Override
    public int getTargetScore() {
        return 400;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level5(game);
    }
}
