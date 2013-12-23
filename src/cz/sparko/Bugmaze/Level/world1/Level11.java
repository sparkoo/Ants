package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level11 extends LevelMinScore {
    public Level11(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, CornerMine.class, LineMine.class, LineNoRotate.class, LineSpeedDown.class, LineSpeedUp.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.64f, 0.09f, 0.05f, 0.02f, 0.06f, 0.06f, 0.03f, 0.025f, 0.025f};
    }

    @Override
    public float getSpeed() {
        return 1f;
    }

    @Override
    public int getTargetScore() {
        return 1100;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level12(game);
    }
}
