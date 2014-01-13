package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.Helper.Coordinate;

import java.util.List;

public class LevelTest extends Level {
    public LevelTest(Game game) {
        super(game);
    }

    @Override
    public Block createRandomBlock(Coordinate coordinate) {
        Class[] blocks = {Corner.class, LineSpeedUp.class, LineSpeedDown.class};
        float[] probabilities = {0.6f, 0.2f, 0.2f};
        int[] walkThroughs = {1, 1, 1};
        try {
            return Block.createRandomBlock(blocks, probabilities, game, coordinate, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Class[] getBlockTypes() {
        return new Class[0];
    }

    @Override
    protected float[] getBlockProbabilities() {
        return new float[0];
    }

    @Override
    public Block[] getLevelBlocks(List<Coordinate> refreshBlocksCoordinates) {
        return new Block[0];
    }

    @Override
    public float getSpeed() {
        return 1;
    }

    @Override
    public void cleanLevelForStart() {

    }
}
