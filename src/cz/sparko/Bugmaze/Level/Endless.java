package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.*;
import cz.sparko.Bugmaze.GameUpdateHandler;
import cz.sparko.Bugmaze.Helper.Coordinate;

public class Endless extends Level {
    public Endless(Game game) {
        super(game);
    }

    @Override
    public Block createRandomBlock(Coordinate coordinate) {
        Class[] blocks = {Corner.class, Line.class, CrossT.class};
        float[] probabilities = {0.6f, 0.1f, 0.3f};
        int[] walkThroughs = {1, 1, 2};
        try {
            return Block.createRandomBlock(blocks, probabilities, walkThroughs, game, coordinate, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Block[] getLevelBlocks() {
        return new Block[0];
    }

    @Override
    public float getSpeed() {
        return 0.7f;
    }

    @Override
    public void cleanLevelForStart() {

    }
}
