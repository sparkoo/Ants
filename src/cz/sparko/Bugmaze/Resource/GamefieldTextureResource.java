package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Game;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;

public class GamefieldTextureResource extends TextureResource {
    public static final int BACKGROUND = 0;

    public GamefieldTextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        super(textureAtlas, game);
    }

    @Override
    protected void loadResources() {
        textures.add(BACKGROUND, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "playgroundBack.png"));
    }
}
