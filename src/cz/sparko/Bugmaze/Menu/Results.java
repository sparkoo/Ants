package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.Endless;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Resource.TextureResource;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;

import java.util.ArrayList;

public class Results extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    ArrayList<TextMenuItem> menuItems = new ArrayList<TextMenuItem>(3);
    private Game game;

    public Results(Camera camera, Game game, long score, float runTime) {
        super(camera);
        this.game = game;
        TextureResource textureResource = game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD);

        final Sprite pausedSprite = new Sprite(0, 0, textureResource.getResource(GamefieldTextureResource.RESULTS_BACKGROUND), game.getVertexBufferObjectManager());
        this.attachChild(pausedSprite);
        this.setBackgroundEnabled(false);

        menuItems.add(new TextMenuItem(0, game.getResourceHandler().getFontIndieFlower36(), "Your score: " + score, game.getVertexBufferObjectManager()));
        menuItems.add(new TextMenuItem(1, game.getResourceHandler().getFontIndieFlower36(), "Play again", game.getVertexBufferObjectManager()));
        menuItems.add(new TextMenuItem(2, game.getResourceHandler().getFontIndieFlower36(), "Back to menu", game.getVertexBufferObjectManager()));
        menuItems.add(new TextMenuItem(0, game.getResourceHandler().getFontIndieFlower36(), "Time: " + runTime, game.getVertexBufferObjectManager()));

        int posX = 0;
        int posY = 0;

        for (TextMenuItem menuItem : menuItems) {
            menuItem.setPosition(posX, posY);
            posY += 50;
            this.addMenuItem(menuItem);
        }
        this.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 1:
                GameManager.getInstance().startGame(GameManager.getInstance().getLevel());
                break;
            case 2:
                GameManager.getInstance().gameOver();
                break;
        }
        return false;
    }
}
