package cz.sparko.Bugmaze.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public class Options extends Menu {
    public static final String SETTINGS_MUSIC = "music";
    public static final String SETTINGS_EFFECTS = "effects";
    public static final String SETTINGS_GRAPHICS = "graphics";

    private final String SHARED_PREFS_KEY = "settings";
    private SharedPreferences prefs;

    private final String ON = "On";
    private final String OFF = "Off";
    private final String LOW = "Low";
    private final String HIGH = "High";

    ArrayList<IMenuItem> menuCustom;

    public Options(Menu prev, MenuActivity menuActivity) {
        super(prev, menuActivity);
    }

    @Override
    protected void setMenuItems() {
        menuItems = new ArrayList<SpriteMenuItem>(4);
        menuItems.add(new SpriteMenuItem(0, menuItemsTextures.get(0), menuActivity.getVertexBufferObjectManager()));
        menuItems.add(new SpriteMenuItem(1, menuItemsTextures.get(1), menuActivity.getVertexBufferObjectManager()));
        menuItems.add(new SpriteMenuItem(2, menuItemsTextures.get(2), menuActivity.getVertexBufferObjectManager()));
        menuItems.add(new SpriteMenuItem(3, menuItemsTextures.get(3), menuActivity.getVertexBufferObjectManager()));
    }

    @Override
    protected void createCustomItems() {
        menuCustom = new ArrayList<IMenuItem>(3);
        menuCustom.add(new TextMenuItem(0, menuActivity.getMenuFont(), getSettings(SETTINGS_MUSIC) ? ON : OFF, menuActivity.getVertexBufferObjectManager()));
        menuCustom.add(new TextMenuItem(1, menuActivity.getMenuFont(), getSettings(SETTINGS_EFFECTS) ? ON : OFF, menuActivity.getVertexBufferObjectManager()));
        menuCustom.add(new TextMenuItem(2, menuActivity.getMenuFont(), getSettings(SETTINGS_GRAPHICS) ? ON : OFF, menuActivity.getVertexBufferObjectManager()));
        int yPosition = 150;
        for (IMenuItem menuItem : menuCustom) {
            menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            menuItem.setPosition(420, yPosition);
            yPosition += 70;
            menuScene.addMenuItem(menuItem);
        }
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
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
            case 3:
                goBack();
                break;
        }
        return false;
    }


    private Boolean toogleSettings(String key) {
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

    protected static void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        menuItemsTextures = new ArrayList<ITiledTextureRegion>(4);
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainPlay.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainLeaderboard.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainOptions.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainHelp.png", 1, 2));
    }
}
