package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Model.GameData;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
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
                goToMenu(this, Menu.menuFactory(MenuEnum.PLAY, game));
                break;
            case 1:
                game.goToLeaderboard();
                break;
            case 2:
                goToMenu(this, Menu.menuFactory(MenuEnum.OPTIONS, game));
                break;
            case 3:
                game.printGameData();
        }
        return false;
    }

    @Override
    protected void loadResources() {
        super.loadResources();

        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.MAIN_PLAY));
        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.MAIN_LEADERBOARD));
        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.MAIN_OPTIONS));
        //menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.MAIN_OPTIONS));

        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.BUG_ICON));
        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.LEADERBOARD_ICON));
        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.OPTIONS_ICON));
        //menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuGeneralTextureResource.OPTIONS_ICON));

        headerTexture = textureResource.getResource(MenuGeneralTextureResource.MAIN_HEADER);
    }
}
