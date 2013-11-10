package cz.sparko.Bugmaze.Character;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Manager.Manager;
import cz.sparko.Bugmaze.PowerUp.InstantRefresh;
import cz.sparko.Bugmaze.Resource.CharacterTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class LadyBug extends Character {
    public LadyBug(float pX, float pY, Game game) {
        super(pX, pY, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.CHARACTER).getResource(CharacterTextureResource.LADYBUG), game);
    }

    @Override
    protected void setPowerUps() {
        powerUps.add(new InstantRefresh(game));
        powerUps.add(new InstantRefresh(game));
        powerUps.add(new InstantRefresh(game));
        powerUps.add(new InstantRefresh(game));
    }
}
