package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Corner;
import cz.sparko.Bugmaze.Block.Cross;
import cz.sparko.Bugmaze.Block.Line;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level1 extends LevelMinScore {
    public Level1(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.7f, 0.25f, 0.05f};
    }

    @Override
    public float getSpeed() {
        return 1.5f;
    }

    @Override
    public int getTargetScore() {
        return 100;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level2(game);
    }
}
