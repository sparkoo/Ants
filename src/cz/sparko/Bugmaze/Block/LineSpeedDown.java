package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Helper.Direction;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.*;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class LineSpeedDown extends LineOneWay implements HasAction {
    public LineSpeedDown(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate , (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_LINE_SPEED_DOWN), game, walkThroughs);
    }

    @Override
    public void doActionBefore() {
        GameManager.getInstance().getCharacter().increaseSpeed(-0.1f);
    }

    @Override
    public void doActionAfter() {

    }
}
