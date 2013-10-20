package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.ArrayList;

public class Main extends Menu {
    public Main(MenuActivity menuActivity) {
        super(null, menuActivity);
    }

    @Override
    protected void setMenuItems() {
        menuItems = new ArrayList<AnimatedSpriteMenuItem>(4);
        menuItems.add(new MenuButton(0, menuItemsTextures.get(0), menuActivity.getVertexBufferObjectManager()));
        menuItems.add(new MenuButton(1, menuItemsTextures.get(1), menuActivity.getVertexBufferObjectManager()));
        menuItems.add(new MenuButton(2, menuItemsTextures.get(2), menuActivity.getVertexBufferObjectManager()));
        menuItems.add(new MenuButton(3, menuItemsTextures.get(3), menuActivity.getVertexBufferObjectManager()));
    }

    @Override
    protected void createCustomItems() {
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 0:
                goToMenu(new Play(this, menuActivity));
                break;
            case 1:
                menuActivity.handleLeaderboard();
                break;
            case 2:
                goToMenu(new Options(this, menuActivity));
                break;
            case 3:
                break;
        }
        return false;
    }

    protected static void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        menuItemsTextures = new ArrayList<ITiledTextureRegion>(4);
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainPlay.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainLeaderboard.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainOptions.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "mainHelp.png", 1, 2));
    }
}
