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
    private BuildableBitmapTextureAtlas textureAtlas;
    private BitmapTextureAtlas fontTextureAtlas;

    public final static int GAMEFIELD = 0;
    public final static int CHARACTER = 1;
    public final static int MENU = 2;

    private ArrayList<TextureResource> textureResourcesList;

    private Font fontIndieFlower100;
    private Font fontIndieFlower36;
    private Font fontIndieFlower40;

    private Music menuMusic;
    private ArrayList<Sound> rebuildSounds;
    private Random randomGenerator = new Random();

    private static ResourceHandler instance;

    private ResourceHandler() {
        textureResourcesList = new ArrayList<TextureResource>();
    }
    public static ResourceHandler getInstance() {
        if (instance != null)
            return instance;
        instance = new ResourceHandler();
        return instance;
    }

    public TextureResource getTextureResource(int textureResourceIndex) { return textureResourcesList.get(textureResourceIndex); }
    public Music getMenuMusic() { return menuMusic; }
    public Sound getRebuildSound() {  return rebuildSounds.get(randomGenerator.nextInt(rebuildSounds.size())); }
    public Font getFontIndieFlower100() { return fontIndieFlower100; }
    public Font getFontIndieFlower40() { return fontIndieFlower40; }
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
        this.fontTextureAtlas = new BitmapTextureAtlas(game.getTextureManager(), 256, 256, game.getSettings(Settings.GRAPHICS) ? TextureOptions.BILINEAR_PREMULTIPLYALPHA : TextureOptions.DEFAULT);
        game.getTextureManager().loadTexture(fontTextureAtlas);
        fontIndieFlower36 = FontFactory.createFromAsset(game.getFontManager(), this.fontTextureAtlas, game.getAssets(), "Indie_Flower.ttf", 36, true, Color.WHITE.getABGRPackedInt());
        fontIndieFlower40 = FontFactory.createFromAsset(game.getFontManager(), this.fontTextureAtlas, game.getAssets(), "Indie_Flower.ttf", 40, true, Color.WHITE.getABGRPackedInt());
        fontIndieFlower100 = FontFactory.createFromAsset(game.getFontManager(), this.fontTextureAtlas, game.getAssets(), "Indie_Flower.ttf", 100, true, Color.WHITE.getABGRPackedInt());
        game.getFontManager().loadFonts(fontIndieFlower36, fontIndieFlower100, fontIndieFlower40);
    }

    private void loadGraphics(Game game) {
        textureAtlas = new BuildableBitmapTextureAtlas(game.getEngine().getTextureManager(), 2048, 2048, game.getSettings(Settings.GRAPHICS) ? TextureOptions.BILINEAR_PREMULTIPLYALPHA : TextureOptions.DEFAULT);
        textureAtlas.clearTextureAtlasSources();

        textureResourcesList.add(GAMEFIELD, new GamefieldTextureResource(textureAtlas, game));
        textureResourcesList.add(CHARACTER, new CharacterTextureResource(textureAtlas, game));
        textureResourcesList.add(MENU, new MenuTextureResource(textureAtlas, game));

        try {
            textureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            textureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
    }
}
