package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Activity.Game;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;

public class MenuGeneralTextureResource extends TextureResource {
    public static final int BACKGROUND = 0;
    public static final int BACK = 1;
    public static final int MAIN_HEADER = 2;
    public static final int MAIN_PLAY = 3;
    public static final int MAIN_LEADERBOARD = 4;
    public static final int MAIN_OPTIONS = 5;
    public static final int BUG_ICON = 6;
    public static final int LEADERBOARD_ICON = 7;
    public static final int OPTIONS_ICON = 8;
    public static final int PLAY_HEADER = 9;
    public static final int PLAY_ENDLESS_MAZE = 10;
    public static final int PLAY_ADVENTURE = 11;
    public static final int PLAY_ICON = 12;
    public static final int PLAY_ICON_NOT = 13;


    public MenuGeneralTextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        super(textureAtlas, game);
    }

    @Override
    protected void loadResources() {
        //general
        textures.add(BACKGROUND, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "menuBack.png"));
        textures.add(BACK, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "backIcon.png", 1, 2));

        //main
        textures.add(MAIN_HEADER, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "mainHeader.png"));
        textures.add(MAIN_PLAY, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "mainPlay.png", 1, 2));
        textures.add(MAIN_LEADERBOARD, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "mainLeaderboard.png", 1, 2));
        textures.add(MAIN_OPTIONS, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "mainOptions.png", 1, 2));
        textures.add(BUG_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "bugIcon.png", 1, 1));
        textures.add(LEADERBOARD_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "leaderboardIcon.png", 1, 1));
        textures.add(OPTIONS_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "optionsIcon.png", 1, 1));

        //play
        textures.add(PLAY_HEADER, BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas, game, "playHeader.png"));
        textures.add(PLAY_ENDLESS_MAZE, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "playEndlessMaze.png", 1, 2));
        textures.add(PLAY_ADVENTURE, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "playAdventure.png", 1, 2));
        textures.add(PLAY_ICON, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "playIcon.png", 1, 2));
        textures.add(PLAY_ICON_NOT, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "playNotIcon.png", 1, 1));
    }
}
