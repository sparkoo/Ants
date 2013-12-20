package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Activity.Game;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;

public class CharacterTextureResource extends TextureResource {
    public static final int LADYBUG = 0;

    public CharacterTextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        super(textureAtlas, game);
    }

    @Override
    protected void loadResources() {
        textures.add(LADYBUG, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "characterLadybug.png", 2, 2));
    }
}
