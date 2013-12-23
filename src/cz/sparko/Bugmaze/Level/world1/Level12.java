package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level12 extends LevelMinScore {
    public Level12(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, CornerMine.class, LineMine.class, LineNoRotate.class, LineSpeedDown.class, LineSpeedUp.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.64f, 0.1f, 0.05f, 0.02f, 0.03f, 0.03f, 0.03f, 0.05f, 0.05f};
    }

    @Override
    public float getSpeed() {
        return 0.9f;
    }

    @Override
    public int getTargetScore() {
        return 1200;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level13(game);
    }
}
