package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.*;
import cz.sparko.Bugmaze.Character;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class BlockLine extends Block{
    public BlockLine(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int walkThroughs) {
        super(coordinate ,pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, walkThroughs);
    }

    @Override
    /*
    TODO: same as BlockCross ==> refactor ?
     */
    public SequenceEntityModifier getMoveHandler(Character character) {
        final float centerX = this.getX() + (SIZE / 2) - (Character.SIZE_X / 2);
        final float centerY = this.getY() + (SIZE / 2) - (cz.sparko.Bugmaze.Character.SIZE_Y / 2);

        final float sourcePositionX = centerX + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getX());
        final float sourcePositionY = centerY + ((SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getY());
        final float outPositionX = centerX + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getX());
        final float outPositionY = centerY + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getY());

        return new SequenceEntityModifier(new MoveModifier(character.getSpeed(), sourcePositionX, outPositionX, sourcePositionY, outPositionY));
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays = new ArrayList<Direction>(2);
        sourceWays.add(0, Direction.LEFT);
        sourceWays.add(1, Direction.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays = new ArrayList<Direction>(2);
        outWays.add(0, Direction.RIGHT);
        outWays.add(1, Direction.LEFT);
    }
}
