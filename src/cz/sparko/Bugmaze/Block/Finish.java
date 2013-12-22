package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Helper.Direction;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

public class Finish extends Block {
    public Finish(Coordinate coordinate, Game game) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_START), game.getVertexBufferObjectManager());
    }

    @Override
    public SequenceEntityModifier getMoveHandler(Character character) {
        final float sourcePositionX = centerX + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getX());
        final float sourcePositionY = centerY + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getY());

        return new SequenceEntityModifier(new MoveModifier(character.getSpeed(), sourcePositionX, centerX, sourcePositionY, centerY));
    }

    @Override
    public boolean delete() {
        if (walkThroughs <= 1) {
            this.deleted = true;
            this.active = false;
        }
        walkThroughs--;
        return deleted;
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays.add(0, Direction.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays.add(0, Direction.LEFT);
    }
}
