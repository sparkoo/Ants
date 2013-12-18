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
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Corner extends Block {
    public Corner(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_CORNER), game.getVertexBufferObjectManager(), walkThroughs);
    }

    protected Corner(Coordinate coordinate, ITiledTextureRegion texture, Game game, int walkThroughs) {
        super(coordinate, texture, game.getVertexBufferObjectManager(), walkThroughs);
    }

    @Override
    public SequenceEntityModifier getMoveHandler(Character character) {
        final float sourcePositionX = centerX + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getX());
        final float sourcePositionY = centerY + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getY());
        final float outPositionX = centerX + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getX());
        final float outPositionY = centerY + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getY());

        return new SequenceEntityModifier(
                new MoveModifier(character.getSpeed() * 0.4f, sourcePositionX, centerX, sourcePositionY, centerY),
                new RotationModifier(character.getSpeed() * 0.2f, character.getRotation(), wayNo == 0 ? character.getRotation() + 90f : character.getRotation() - 90f),
                new MoveModifier(character.getSpeed() * 0.4f, centerX, outPositionX, centerY, outPositionY));
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays.add(0, Direction.DOWN);
        sourceWays.add(1, Direction.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays.add(0, Direction.RIGHT);
        outWays.add(1, Direction.DOWN);
    }
}
