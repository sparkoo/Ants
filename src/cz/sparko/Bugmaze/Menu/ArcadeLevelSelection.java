package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class ArcadeLevelSelection extends Menu {
    TwoStateMenuButton[] levelButtons;

    public ArcadeLevelSelection(Game game) {
        super(game);
        levelButtons = new TwoStateMenuButton[20];
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

        int posX = 100;
        int posY = 150;
        for (int i = 0; i < 20; i++) {
            if (i > 0 && i % 5 == 0) {
                posX = 100;
                posY += 80;
            }
            levelButtons[i] = new TwoStateMenuButton(i, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.MENU_LEVEL).getResource(i), game.getVertexBufferObjectManager());
            levelButtons[i].setPosition(posX, posY);
            menuScene.addMenuItem(levelButtons[i]);

            posX += 90;
        }

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

        headerTexture = textureResource.getResource(MenuGeneralTextureResource.WORLD_GRASS_HEADER);
    }
}
