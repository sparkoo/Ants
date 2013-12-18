package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Level.Endless;
import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.LevelsTextureResource;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import java.lang.reflect.Constructor;

public class ArcadeLevelSelection extends Menu {
    IMenuItem[] levelButtons;

    Class[] levels = new Class[20];

    public ArcadeLevelSelection(Game game) {
        super(game);
        levelButtons = new IMenuItem[20];

        for (int i = 0; i < 20; i++) {
            try {
                levels[i] = Class.forName("cz.sparko.Bugmaze.Level.World1.Level" + ( i + 1 ));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (!game.getSharedPreferencesBoolean(levels[0].getName(), false))
            game.setSharedPreferencesBoolean(levels[0].getName(), true);
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
            if (game.getSharedPreferencesBoolean(levels[i].getName(), false)) {
                levelButtons[i] = new TwoStateMenuButton(i, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.MENU_LEVEL).getResource(i), game.getVertexBufferObjectManager());
            } else {
                levelButtons[i] = new SpriteMenuItem(i, game.getResourceHandler().getTextureResource(ResourceHandler.MENU_LEVEL).getResource(LevelsTextureResource.LOCKED), game.getVertexBufferObjectManager());
            }
            levelButtons[i].setPosition(posX, posY);
            menuScene.addMenuItem(levelButtons[i]);

            posX += 90;
        }

        menuScene.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        int clickedItem = pMenuItem.getID();
        if (clickedItem == -1) {
            goBack();
            return true;
        }
        if (game.getSharedPreferencesBoolean(levels[clickedItem].getName(), false)) {
            try {
                Constructor<Level> constructor = levels[clickedItem].getConstructor(Game.class);
                GameManager.getInstance().startGame(constructor.newInstance(game));
            } catch (Exception e) {
                e.printStackTrace();
                goBack();
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadResources() {
        super.loadResources();

        headerTexture = textureResource.getResource(MenuGeneralTextureResource.WORLD_GRASS_HEADER);
    }
}
