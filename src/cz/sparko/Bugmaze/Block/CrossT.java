package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Helper.Direction;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import java.util.Random;

public class CrossT extends Block {
    public CrossT(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_CROSS_T), game.getVertexBufferObjectManager(), walkThroughs);
    }

    @Override
    public SequenceEntityModifier getMoveHandler(Character character) {
        final float sourcePositionX = centerX + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getX());
        final float sourcePositionY = centerY + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getY());
        final float outPositionX = centerX + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getX());
        final float outPositionY = centerY + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getY());

        if (wayNo == 0) {
            return new SequenceEntityModifier(
                    new MoveModifier(character.getSpeed() * 0.4f, sourcePositionX, centerX, sourcePositionY, centerY),
                    new RotationModifier(character.getSpeed() * 0.2f, character.getRotation(), character.getRotation() + sourceWays.get(wayNo).getCornerWay(outWays.get(wayNo))),
                    new MoveModifier(character.getSpeed() * 0.4f, centerX, outPositionX, centerY, outPositionY));
        } else {
            return new SequenceEntityModifier(new MoveModifier(character.getSpeed(), sourcePositionX, outPositionX, sourcePositionY, outPositionY));
        }
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays.add(0, Direction.LEFT);
        sourceWays.add(1, Direction.DOWN);
        sourceWays.add(2, Direction.UP);
    }

    @Override
    public void setOutWays() {
        Random rnd = new Random();
        outWays.add(0, rnd.nextBoolean() ? Direction.DOWN : Direction.UP);
        outWays.add(1, Direction.UP);
        outWays.add(2, Direction.DOWN);
    }
}
