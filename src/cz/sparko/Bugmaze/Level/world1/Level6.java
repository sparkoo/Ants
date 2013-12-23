package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level6 extends LevelMinScore {
    public Level6(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, LineOneWay.class, LineSpeedUp.class, LineSpeedDown.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.68f, 0.15f, 0.03f, 0.04f, 0.05f, 0.025f, 0.025f};
    }

    @Override
    public float getSpeed() {
        return 1.1f;
    }

    @Override
    public int getTargetScore() {
        return 600;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level7(game);
    }
}
