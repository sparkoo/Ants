package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Block.Corner;
import cz.sparko.Bugmaze.Block.Cross;
import cz.sparko.Bugmaze.Block.Line;

public class Endless extends Level {
    public Endless(Game game) {
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
    public Block[] getLevelBlocks() {
        return new Block[0];
    }

    @Override
    public float getSpeed() {
        return 0.8f;
    }

    @Override
    public void cleanLevelForStart() {

    }
}
