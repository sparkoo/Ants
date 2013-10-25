package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.ArrayList;

public class Main extends Menu {
    public Main(BaseGameActivity menuActivity, BuildableBitmapTextureAtlas mBitmapTextureAtlas, Camera camera) {
        super(menuActivity, mBitmapTextureAtlas, camera);
    }



    @Override
    protected void createCustomItems() {
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 0:
                goToMenu(this, menuList.get(MenuEnum.PLAY.getValue()));
                break;
            case 1:
                menuActivity.handleLeaderboard();
                break;
            case 2:
                goToMenu(this, menuList.get(MenuEnum.OPTIONS.getValue()));
                break;
        }
        return false;
    }

    @Override
    protected void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
        super.loadResources(mBitmapTextureAtlas);

        menuItemsTextures = new ArrayList<ITiledTextureRegion>(3);
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "mainPlay.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "mainLeaderboard.png", 1, 2));
        menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "mainOptions.png", 1, 2));
        //menuItemsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "mainHelp.png", 1, 2));

        menuIconsTextures = new ArrayList<ITiledTextureRegion>(3);
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "bugIcon.png", 1, 1));
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "leaderboardIcon.png", 1, 1));
        menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "optionsIcon.png", 1, 1));
        //menuIconsTextures.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "helpIcon.png", 1, 1));

        headerTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, menuActivity.getAssets(), "mainHeader.png", false);
    }
}
