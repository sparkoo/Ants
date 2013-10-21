package cz.sparko.Bugmaze.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public class Options extends Menu {
    //TODO: create options enum
    public static final String SETTINGS_MUSIC = "music";
    public static final String SETTINGS_EFFECTS = "effects";
    public static final String SETTINGS_GRAPHICS = "graphics";

    private final String SHARED_PREFS_KEY = "settings";
    private SharedPreferences prefs;

    private final String ON = "On";
    private final String OFF = "Off";
    private final String LOW = "Low";
    private final String HIGH = "High";

    ArrayList<AnimatedSpriteMenuItem> menuCustom;
    ArrayList<ITiledTextureRegion> menuCustomTextures;

    public Options(MenuActivity menuActivity, BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
        super(menuActivity, mBitmapTextureAtlas);
    }

    @Override
    protected void createCustomItems() {
        menuCustom = new ArrayList<AnimatedSpriteMenuItem>(3);
        menuCustom.add(new AnimatedSpriteMenuItem(0, menuCustomTextures.get(0), menuActivity.getVertexBufferObjectManager()));
        menuCustom.add(new AnimatedSpriteMenuItem(1, menuCustomTextures.get(0), menuActivity.getVertexBufferObjectManager()));
        menuCustom.add(new AnimatedSpriteMenuItem(2, menuCustomTextures.get(1), menuActivity.getVertexBufferObjectManager()));
        int yPosition = 150;
        for (IMenuItem menuItem : menuCustom) {
            menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            menuItem.setPosition(440, yPosition);
            yPosition += 70;
            menuScene.addMenuItem(menuItem);
        }
        refresh();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case -1:
                goBack();
                break;
            case 0:
                if (toogleSettings(SETTINGS_MUSIC))
                    menuActivity.playMusic();
                else
                    menuActivity.pauseMusic();
                break;
            case 1:
                toogleSettings(SETTINGS_EFFECTS);
                break;
            case 2:
                toogleSettings(SETTINGS_GRAPHICS);
                break;
        }
        refresh();
        return false;
    }

    private void refresh() {
        if (getSettings(SETTINGS_MUSIC)) {
            menuCustom.get(0).setCurrentTileIndex(0);
            menuIcons.get(0).setCurrentTileIndex(0);
        } else {
            menuCustom.get(0).setCurrentTileIndex(1);
            menuIcons.get(0).setCurrentTileIndex(1);
        }

        if (getSettings(SETTINGS_EFFECTS)) {
            menuCustom.get(1).setCurrentTileIndex(0);
            menuIcons.get(1).setCurrentTileIndex(0);
        } else {
            menuCustom.get(1).setCurrentTileIndex(1);
            menuIcons.get(1).setCurrentTileIndex(1);
        }

        if (getSettings(SETTINGS_GRAPHICS)) {
            menuCustom.get(2).setCurrentTileIndex(0);
            menuIcons.get(2).setCurrentTileIndex(0);
        } else {
            menuCustom.get(2).setCurrentTileIndex(1);
            menuIcons.get(2).setCurrentTileIndex(1);
        }
    }

    private Boolean toogleSettings(String key) {
        if (prefs == null)
            prefs = menuActivity.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Boolean newValue;
        if (prefs.getBoolean(key, true))
            newValue = false;
        else
            newValue = true;
        editor.putBoolean(key, newValue);
        editor.commit();
        return newValue;
    }

    private Boolean getSettings(String key) {
        if (prefs == null)
            prefs = menuActivity.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, true);
    }

    @Override
    protected void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
        super.loadResources(mBitmapTextureAtlas);

        menuItemsTextures = new ArrayList<ITiledTextureRegion>(3);
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsMusic.png", 1, 1));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsEffects.png", 1, 1));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsGraphics.png", 1, 1));

        menuIconsTextures = new ArrayList<ITiledTextureRegion>(3);
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsMusicIcon.png", 1, 2));
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsMusicIcon.png", 1, 2));
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsGraphicsIcon.png", 1, 2));

        menuCustomTextures = new ArrayList<ITiledTextureRegion>(2);
        menuCustomTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsOnOff.png", 1, 2));
        menuCustomTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsHighLow.png", 1, 2));

        headerTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, menuActivity.getAssets(), "optionsHeader.png", false);
    }
}
