package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Activity.Game;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;

public class LevelsTextureResource extends TextureResource {
    public static final int LEVEL1 = 0;
    public static final int LEVEL2 = 1;
    public static final int LEVEL3 = 2;
    public static final int LEVEL4 = 3;
    public static final int LEVEL5 = 4;
    public static final int LEVEL6 = 5;
    public static final int LEVEL7 = 6;
    public static final int LEVEL8 = 7;
    public static final int LEVEL9 = 8;
    public static final int LEVEL10 = 9;
    public static final int LEVEL11 = 10;
    public static final int LEVEL12 = 11;
    public static final int LEVEL13 = 12;
    public static final int LEVEL14 = 13;
    public static final int LEVEL15 = 14;
    public static final int LEVEL16 = 15;
    public static final int LEVEL17 = 16;
    public static final int LEVEL18 = 17;
    public static final int LEVEL19 = 18;
    public static final int LEVEL20 = 19;

    public LevelsTextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        super(textureAtlas, game);
    }

    @Override
    protected void loadResources() {
        textures.add(LEVEL1, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "01.png", 1, 2));
        textures.add(LEVEL2, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "02.png", 1, 2));
        textures.add(LEVEL3, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "03.png", 1, 2));
        textures.add(LEVEL4, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "04.png", 1, 2));
        textures.add(LEVEL5, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "05.png", 1, 2));
        textures.add(LEVEL6, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "06.png", 1, 2));
        textures.add(LEVEL7, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "07.png", 1, 2));
        textures.add(LEVEL8, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "08.png", 1, 2));
        textures.add(LEVEL9, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "09.png", 1, 2));
        textures.add(LEVEL10, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "10.png", 1, 2));
        textures.add(LEVEL11, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "11.png", 1, 2));
        textures.add(LEVEL12, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "12.png", 1, 2));
        textures.add(LEVEL13, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "13.png", 1, 2));
        textures.add(LEVEL14, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "14.png", 1, 2));
        textures.add(LEVEL15, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "15.png", 1, 2));
        textures.add(LEVEL16, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "16.png", 1, 2));
        textures.add(LEVEL17, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "17.png", 1, 2));
        textures.add(LEVEL18, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "18.png", 1, 2));
        textures.add(LEVEL19, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "19.png", 1, 2));
        textures.add(LEVEL20, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "20.png", 1, 2));
    }
}
