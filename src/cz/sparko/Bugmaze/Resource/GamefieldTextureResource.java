package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Activity.Game;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;

public class GamefieldTextureResource extends TextureResource {
    public static final int BACKGROUND = 0;
    public static final int BLOCK_CORNER = 1;
    public static final int BLOCK_CROSS = 2;
    public static final int BLOCK_LINE = 3;
    public static final int BLOCK_START = 4;
    public static final int PAUSE_BUTTON = 5;
    public static final int PAUSE_BACKGROUND = 6;

    public GamefieldTextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        super(textureAtlas, game);
    }

    @Override
    protected void loadResources() {
        textures.add(BACKGROUND, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "playgroundBack.png"));

        textures.add(BLOCK_CORNER, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "corner.png", 4, 2));
        textures.add(BLOCK_CROSS, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "cross.png", 1, 2));
        textures.add(BLOCK_LINE, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "line.png", 2, 2));
        textures.add(BLOCK_START, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "start.png", 4, 2));
        textures.add(PAUSE_BUTTON, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "pauseIcon.png"));
        textures.add(PAUSE_BACKGROUND, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "pauseBackground.png"));
    }
}
