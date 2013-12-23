package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level14 extends LevelMinScore {
    public Level14(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, CornerNoRotate.class, LineNoRotate.class, LineSpeedDown.class, LineSpeedUp.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.65f, 0.15f, 0.05f, 0.02f, 0.01f, 0.02f, 0.05f, 0.05f};
    }

    @Override
    public float getSpeed() {
        return 0.9f;
    }

    @Override
    public int getTargetScore() {
        return 1400;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level15(game);
    }
}
