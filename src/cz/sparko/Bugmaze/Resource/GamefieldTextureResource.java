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
    public static final int RESULTS_BACKGROUND = 7;
    public static final int POWER_UP_BUTTON = 8;
    public static final int PAUSE_CONTINUE = 9;
    public static final int PAUSE_CONTINUE_ICON = 10;
    public static final int PAUSE_PLAY_AGAIN = 11;
    public static final int PAUSE_PLAY_AGAIN_ICON = 12;
    public static final int PAUSE_RETURN = 13;
    public static final int PAUSE_RETURN_ICON = 14;
    public static final int PAUSE_MUSIC_ICON = 15;


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
        textures.add(PAUSE_BUTTON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pauseIcon.png", 1, 2));
        textures.add(PAUSE_BACKGROUND, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "pauseBackground.png"));
        textures.add(RESULTS_BACKGROUND, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "resultsBackground.png"));
        textures.add(POWER_UP_BUTTON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "powerUpBtn.png", 2, 1));
        textures.add(PAUSE_CONTINUE, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pauseContinue.png", 1, 2));
        textures.add(PAUSE_CONTINUE_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pausePlayIcon.png", 1, 2));
        textures.add(PAUSE_PLAY_AGAIN, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pausePlayAgain.png", 1, 2));
        textures.add(PAUSE_PLAY_AGAIN_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pausePlayAgainIcon.png", 1, 2));
        textures.add(PAUSE_RETURN, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pauseReturn.png", 1, 2));
        textures.add(PAUSE_RETURN_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pauseMenuIcon.png", 1, 2));
        textures.add(PAUSE_MUSIC_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "pauseMusicIcon.png", 1, 2));
    }
}
