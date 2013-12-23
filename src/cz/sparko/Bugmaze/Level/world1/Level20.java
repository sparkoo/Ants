package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level20 extends LevelMinScore {
    public Level20(Game game) {
        super(game);
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[]{Corner.class, Line.class, Cross.class, CrossT.class, CornerNoRotate.class, CornerAutoRotate.class, LineNoRotate.class, LineAutoRotate.class, LineSpeedDown.class, LineSpeedUp.class};
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[]{0.65f, 0.15f, 0.05f, 0.01f, 0.01f, 0.02f, 0.01f, 0.02f, 0.04f, 0.04f};
    }

    @Override
    public float getSpeed() {
        return 0.7f;
    }

    @Override
    public int getTargetScore() {
        return 2000;
    }
}
