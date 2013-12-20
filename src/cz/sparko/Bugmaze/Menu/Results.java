package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Resource.TextureResource;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import java.util.ArrayList;

public class Results extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    ArrayList<IMenuItem> menuItems = new ArrayList<IMenuItem>(3);
    ArrayList<IMenuItem> menuIcons = new ArrayList<IMenuItem>(3);
    private Game game;

    public Results(Camera camera, Game game, long score, float runTime, Level level, boolean completed) {
        super(camera);
        this.game = game;
        TextureResource textureResource = game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD);
        TextureResource menuTextureResource = game.getResourceHandler().getTextureResource(ResourceHandler.MENU_GENERAL);

        final Sprite pausedSprite = new Sprite(0, 0, textureResource.getResource(GamefieldTextureResource.RESULTS_BACKGROUND), game.getVertexBufferObjectManager());
        attachChild(pausedSprite);
        setBackgroundEnabled(false);

        ITextureRegion headerText;
        if (completed)
            headerText = textureResource.getResource(GamefieldTextureResource.RESULTS_COMPLETED);
        else
            headerText = textureResource.getResource(GamefieldTextureResource.RESULTS_FAILED);

        Sprite header = new Sprite(170, 30, headerText, game.getVertexBufferObjectManager());
        attachChild(header);



        if (completed) {
            menuItems.add(new TwoStateMenuButton(0, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.RESULTS_NEXT_LEVEL), game.getVertexBufferObjectManager()));
            menuIcons.add(new TwoStateMenuButton(0, (ITiledTextureRegion)menuTextureResource.getResource(MenuGeneralTextureResource.PLAY_ICON), game.getVertexBufferObjectManager()));
        }

        menuItems.add(new TwoStateMenuButton(1, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.RESULTS_PLAY_AGAIN), game.getVertexBufferObjectManager()));
        menuItems.add(new TwoStateMenuButton(2, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.RESULTS_RETURN_TO_MENU), game.getVertexBufferObjectManager()));
        menuItems.add(new TextMenuItem(-1, game.getResourceHandler().getFontIndieFlower36(), "Your score: " + score, game.getVertexBufferObjectManager()));

        menuIcons.add(new TwoStateMenuButton(1, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.PAUSE_PLAY_AGAIN_ICON), game.getVertexBufferObjectManager()));
        menuIcons.add(new TwoStateMenuButton(2, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.PAUSE_RETURN_ICON), game.getVertexBufferObjectManager()));

        int posX = 250, posY = 150;

        for (IMenuItem menuItem : menuItems) {
            menuItem.setPosition(posX, posY);
            posY += 70;
            this.addMenuItem(menuItem);
        }

        posX = 180; posY = 160;
        for (IMenuItem menuIcon : menuIcons) {
            menuIcon.setPosition(posX, posY);
            posY += 70;
            this.addMenuItem(menuIcon);
        }

        this.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 0:
                GameManager.getInstance().startGame(GameManager.getInstance().getLevel().getNextLevel());
                break;
            case 1:
                GameManager.getInstance().getLevel().cleanLevelForStart();
                GameManager.getInstance().startGame(GameManager.getInstance().getLevel());
                break;
            case 2:
                GameManager.getInstance().gameOver();
                break;
        }
        return false;
    }
}
