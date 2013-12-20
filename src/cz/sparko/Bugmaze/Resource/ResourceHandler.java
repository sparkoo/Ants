package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Settings;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.util.color.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ResourceHandler {
    private BuildableBitmapTextureAtlas textureAtlasMenu;
    private BuildableBitmapTextureAtlas textureAtlasGameField;
    private BuildableBitmapTextureAtlas textureAtlasMenuOptions;
    private BuildableBitmapTextureAtlas textureAtlasLevel;
    private BitmapTextureAtlas fontTextureAtlas;

    public final static int GAMEFIELD = 0;
    public final static int CHARACTER = 1;
    public final static int MENU_GENERAL = 2;
    public final static int MENU_OPTIONS = 3;
    public final static int MENU_LEVEL = 4;

    private ArrayList<TextureResource> textureResourcesList;

    private Font fontIndieFlower36;

    private Music menuMusic;
    private ArrayList<Sound> rebuildSounds;
    private Random randomGenerator = new Random();

    public ResourceHandler(Game game) {
        textureResourcesList = new ArrayList<TextureResource>();
        loadResource(game);
    }

    public TextureResource getTextureResource(int textureResourceIndex) { return textureResourcesList.get(textureResourceIndex); }
    public Music getMenuMusic() { return menuMusic; }
    public Sound getRebuildSound() {  return rebuildSounds.get(randomGenerator.nextInt(rebuildSounds.size())); }
    public Font getFontIndieFlower36() { return fontIndieFlower36; }

    public void loadResource(Game game) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        FontFactory.setAssetBasePath("font/");
        SoundFactory.setAssetBasePath("sfx/");
        MusicFactory.setAssetBasePath("sfx/");

        loadGraphics(game);
        loadFonts(game);
        loadSounds(game);
    }

    private void loadSounds(Game game) {
        rebuildSounds = new ArrayList<Sound>();
        try {
            rebuildSounds.add(SoundFactory.createSoundFromAsset(game.getSoundManager(), game, "rebuild1.ogg"));
            rebuildSounds.add(SoundFactory.createSoundFromAsset(game.getSoundManager(), game, "rebuild2.ogg"));
            menuMusic = MusicFactory.createMusicFromAsset(game.getMusicManager(), game, "menuMusic.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFonts(Game game) {
        this.fontTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, game.getSettingsBoolean(Settings.GRAPHICS) ? TextureOptions.BILINEAR_PREMULTIPLYALPHA : TextureOptions.DEFAULT);
        game.getTextureManager().loadTexture(fontTextureAtlas);
        fontIndieFlower36 = FontFactory.createFromAsset(game.getFontManager(), this.fontTextureAtlas, game.getAssets(), "Indie_Flower.ttf", 36, true, Color.WHITE.getABGRPackedInt());
        game.getFontManager().loadFonts(fontIndieFlower36);
    }

    private void loadGraphics(Game game) {
        textureAtlasGameField = new BuildableBitmapTextureAtlas(game.getEngine().getTextureManager(), 2048, 2048, game.getSettingsBoolean(Settings.GRAPHICS) ? TextureOptions.BILINEAR_PREMULTIPLYALPHA : TextureOptions.DEFAULT);
        textureAtlasGameField.clearTextureAtlasSources();
        textureResourcesList.add(GAMEFIELD, new GamefieldTextureResource(textureAtlasGameField, game));
        textureResourcesList.add(CHARACTER, new CharacterTextureResource(textureAtlasGameField, game));

        textureAtlasMenu = new BuildableBitmapTextureAtlas(game.getEngine().getTextureManager(), 1024, 1024, game.getSettingsBoolean(Settings.GRAPHICS) ? TextureOptions.BILINEAR_PREMULTIPLYALPHA : TextureOptions.DEFAULT);
        textureAtlasMenu.clearTextureAtlasSources();
        textureResourcesList.add(MENU_GENERAL, new MenuGeneralTextureResource(textureAtlasMenu, game));

        textureAtlasMenuOptions = new BuildableBitmapTextureAtlas(game.getEngine().getTextureManager(), 1024, 1024, game.getSettingsBoolean(Settings.GRAPHICS) ? TextureOptions.BILINEAR_PREMULTIPLYALPHA : TextureOptions.DEFAULT);
        textureAtlasMenuOptions.clearTextureAtlasSources();
        textureResourcesList.add(MENU_OPTIONS, new MenuOptionsTextureResource(textureAtlasMenuOptions, game));

        textureAtlasLevel = new BuildableBitmapTextureAtlas(game.getEngine().getTextureManager(), 1024, 1024, game.getSettingsBoolean(Settings.GRAPHICS) ? TextureOptions.BILINEAR_PREMULTIPLYALPHA : TextureOptions.DEFAULT);
        textureAtlasLevel.clearTextureAtlasSources();
        textureResourcesList.add(MENU_LEVEL, new LevelsTextureResource(textureAtlasLevel, game));


        try {
            textureAtlasMenu.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            textureAtlasMenu.load();
            textureAtlasGameField.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            textureAtlasGameField.load();
            textureAtlasMenuOptions.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            textureAtlasMenuOptions.load();
            textureAtlasLevel.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            textureAtlasLevel.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
    }

}
