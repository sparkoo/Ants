package cz.sparko.Bugmaze.Character;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.PowerUp.Points2xBase;
import cz.sparko.Bugmaze.Resource.CharacterTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class LadyBug extends Character {
    public LadyBug(float pX, float pY, Game game, float speed) {
        super(pX, pY, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.CHARACTER).getResource(CharacterTextureResource.LADYBUG), game, speed);
    }

    @Override
    protected void setPowerUps() {
        //powerUps.add(new Points2xBase(game));
        //powerUps.add(new InstantRefresh(game));
        //powerUps.add(new InstantRefresh(game));
        //powerUps.add(new InstantRefresh(game));
    }
}
