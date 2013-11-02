package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Activity.Game;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;

public class MenuOptionsTextureResource extends TextureResource {
    public static final int OPTIONS_HEADER = 0;
    public static final int OPTIONS_MUSIC = 1;
    public static final int OPTIONS_EFFECTS = 2;
    public static final int OPTIONS_GRAPHICS = 3;
    public static final int OPTIONS_MUSIC_ICON = 4;
    public static final int OPTIONS_GRAPHICS_ICON = 5;
    public static final int OPTIONS_ON_OFF = 6;
    public static final int OPTIONS_HIGH_LOW = 7;
    public MenuOptionsTextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        super(textureAtlas, game);
    }

    @Override
    protected void loadResources() {
        //options
        textures.add(OPTIONS_HEADER, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "optionsHeader.png"));
        textures.add(OPTIONS_MUSIC, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsMusic.png", 1, 2));
        textures.add(OPTIONS_EFFECTS, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsEffects.png", 1, 2));
        textures.add(OPTIONS_GRAPHICS, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsGraphics.png", 1, 2));
        textures.add(OPTIONS_MUSIC_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsMusicIcon.png", 1, 2));
        textures.add(OPTIONS_GRAPHICS_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsGraphicsIcon.png", 1, 2));
        textures.add(OPTIONS_ON_OFF, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsOnOff.png", 1, 2));
        textures.add(OPTIONS_HIGH_LOW, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsHighLow.png", 1, 2));
    }
}
