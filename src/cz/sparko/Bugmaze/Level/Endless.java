package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Block.Corner;
import cz.sparko.Bugmaze.Block.Cross;
import cz.sparko.Bugmaze.Block.Line;
import cz.sparko.Bugmaze.Helper.Coordinate;

public class Endless extends Level {
    public Endless(Game game) {
        super(game);
    }

    @Override
    public void reachedNextBlock() {

    }

    @Override
    public Block createRandomBlock(Coordinate coordinate) {
        Class[] blocks = {Corner.class, Line.class, Cross.class};
        float[] probabilities = {0.7f, 0.2f, 0.1f};
        int[] walkThroughs = {1, 1, 2};
        try {
            return Block.createRandomBlock(blocks, probabilities, walkThroughs, game, coordinate, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
