package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Corner;
import cz.sparko.Bugmaze.Block.Cross;
import cz.sparko.Bugmaze.Block.Line;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level2 extends LevelMinScore {
    public Level2(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.7f, 0.2f, 0.1f};
    }

    @Override
    protected int[] getBlockWalkThroughs() {
        return new int[]{1, 1, 2};
    }

    @Override
    public float getSpeed() {
        return 1.3f;
    }

    @Override
    public int getTargetScore() {
        return 250;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level3(game);
    }
}
