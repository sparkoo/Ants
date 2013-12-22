package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Helper.Direction;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Cross extends Block {
    public Cross(Coordinate coordinate, Game game) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_CROSS), game.getVertexBufferObjectManager(), 2);
    }

    @Override
    /*
    TODO: same as Line ==> refactor ?
     */
    public SequenceEntityModifier getMoveHandler(Character character) {
        final float sourcePositionX = centerX + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getX());
        final float sourcePositionY = centerY + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getY());
        final float outPositionX = centerX + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getX());
        final float outPositionY = centerY + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getY());

        return new SequenceEntityModifier(new MoveModifier(character.getSpeed(), sourcePositionX, outPositionX, sourcePositionY, outPositionY));
    }

    @Override
    public void refreshWalkthroughs() {
        this.walkThroughs = 2;
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays.add(0, Direction.UP);
        sourceWays.add(1, Direction.DOWN);
        sourceWays.add(2, Direction.LEFT);
        sourceWays.add(3, Direction.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays.add(0, Direction.DOWN);
        outWays.add(1, Direction.UP);
        outWays.add(2, Direction.RIGHT);
        outWays.add(3, Direction.LEFT);
    }
}
