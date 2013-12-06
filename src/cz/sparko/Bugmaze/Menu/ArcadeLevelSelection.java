package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;

public class ArcadeLevelSelection extends Menu {
    public ArcadeLevelSelection(Game game) {
        super(game);
    }

    @Override
    protected void createCustomItems() {

    }

    @Override
    protected void createMenuScene() {
        menuScene = new MenuScene(game.getCamera());
        menuScene.setBackgroundEnabled(false);

        header = new Sprite(180, 30, headerTexture, game.getVertexBufferObjectManager());
        menuScene.attachChild(header);

        backBtn.setPosition(100, 50);
        menuScene.addMenuItem(backBtn);
        
        menuScene.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case -1:
                goBack();
                break;
        }
        return false;
    }

    @Override
    public void loadResources() {
        super.loadResources();

        headerTexture = textureResource.getResource(MenuGeneralTextureResource.PLAY_ADVENTURE);
    }
}
