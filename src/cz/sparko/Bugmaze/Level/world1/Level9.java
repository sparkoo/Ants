package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level9 extends LevelMinScore {
    public Level9(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, CornerMine.class, LineOneWay.class, LineMine.class, LineSpeedUp.class, LineSpeedDown.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.65f, 0.05f, 0.04f, 0.01f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f};
    }

    @Override
    public float getSpeed() {
        return 1f;
    }

    @Override
    public int getTargetScore() {
        return 900;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level10(game);
    }
}
