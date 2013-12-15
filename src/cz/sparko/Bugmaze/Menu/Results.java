package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Resource.TextureResource;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import java.util.ArrayList;

public class Results extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    ArrayList<IMenuItem> menuItems = new ArrayList<IMenuItem>(3);
    private Game game;

    public Results(Camera camera, Game game, long score, float runTime, Level level) {
        super(camera);
        this.game = game;
        TextureResource textureResource = game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD);

        final Sprite pausedSprite = new Sprite(0, 0, textureResource.getResource(GamefieldTextureResource.RESULTS_BACKGROUND), game.getVertexBufferObjectManager());
        attachChild(pausedSprite);
        setBackgroundEnabled(false);

        Sprite header = new Sprite(170, 30, textureResource.getResource(GamefieldTextureResource.RESULTS_COMPLETED), game.getVertexBufferObjectManager());
        attachChild(header);

        menuItems.add(new TwoStateMenuButton(1, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.RESULTS_NEXT_LEVEL), game.getVertexBufferObjectManager()));
        menuItems.add(new TwoStateMenuButton(1, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.RESULTS_PLAY_AGAIN), game.getVertexBufferObjectManager()));
        menuItems.add(new TwoStateMenuButton(2, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.RESULTS_RETURN_TO_MENU), game.getVertexBufferObjectManager()));
        menuItems.add(new TextMenuItem(0, game.getResourceHandler().getFontIndieFlower36(), "Your score: " + score, game.getVertexBufferObjectManager()));

        int posX = 250;
        int posY = 150;

        for (IMenuItem menuItem : menuItems) {
            menuItem.setPosition(posX, posY);
            posY += 70;
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
