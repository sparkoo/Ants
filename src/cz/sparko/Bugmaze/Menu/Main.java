package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Resource.MenuTextureResource;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Main extends Menu {
    public Main(Game game) {
        super(game);
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
                game.goToLeaderboard();
                break;
            case 2:
                goToMenu(this, menuList.get(MenuEnum.OPTIONS.getValue()));
                break;
        }
        return false;
    }

    @Override
    protected void loadResources() {
        super.loadResources();

        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.MAIN_PLAY));
        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.MAIN_LEADERBOARD));
        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.MAIN_OPTIONS));

        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.BUG_ICON));
        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.LEADERBOARD_ICON));
        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.OPTIONS_ICON));

        headerTexture = textureResource.getResource(MenuTextureResource.MAIN_HEADER);
    }
}
