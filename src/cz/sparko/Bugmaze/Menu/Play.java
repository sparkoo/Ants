package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.Endless;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Play extends Menu {
    public Play(Game game) {
        super(game);
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
                GameManager.getInstance().startGame(new Endless(game));
                break;
            case 1:
                //TODO: show some message comming soon ...
                break;
        }
        return false;
    }

    @Override
    protected void loadResources() {
        super.loadResources();

        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.PLAY_ENDLESS_MAZE));
        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.PLAY_ADVENTURE));

        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.PLAY_ICON));
        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.PLAY_ICON_NOT));

        headerTexture = textureResource.getResource(MenuGeneralTextureResource.PLAY_HEADER);
    }
}
