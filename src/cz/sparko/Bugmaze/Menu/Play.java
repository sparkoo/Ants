package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import java.util.ArrayList;

public class Play extends Menu {
    public Play(MenuActivity menuActivity, BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
        super(menuActivity, mBitmapTextureAtlas);
    }

    @Override
    protected void createCustomItems() {
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case -1:
                goBack();
                break;
            case 0:
                menuActivity.startGame();
                break;
            case 1:
                //TODO: show some message comming soon ...
                break;
        }
        return false;
    }

    @Override
    protected void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
        super.loadResources(mBitmapTextureAtlas);

        menuItemsTextures = new ArrayList<ITiledTextureRegion>(2);
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "playEndlessMaze.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "playAdventure.png", 1, 1));

        menuIconsTextures = new ArrayList<ITiledTextureRegion>(2);
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "playIcon.png", 1, 2));
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "playNotIcon.png", 1, 1));

        headerTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, menuActivity.getAssets(), "playHeader.png", false);
    }
}
