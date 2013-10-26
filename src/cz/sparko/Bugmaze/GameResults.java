package cz.sparko.Bugmaze;

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
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class GameResults extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    ArrayList<TextMenuItem> menuItems = new ArrayList<TextMenuItem>(3);

    public GameResults(Camera camera, final Scene gameScene, VertexBufferObjectManager vertexBufferObjectManager, int score) {
        super(camera);
        TextureResource textureResource = ResourceHandler.getInstance().getTextureResource(ResourceHandler.GAMEFIELD);

        final Sprite pausedSprite = new Sprite(0, 0, textureResource.getResource(GamefieldTextureResource.BACKGROUND), vertexBufferObjectManager);
        this.attachChild(pausedSprite);
        this.setBackgroundEnabled(false);

        menuItems.add(new TextMenuItem(0, ResourceHandler.getInstance().getFontIndieFlower36(), "Your score: " + score, vertexBufferObjectManager));
        menuItems.add(new TextMenuItem(1, ResourceHandler.getInstance().getFontIndieFlower36(), "Play again", vertexBufferObjectManager));
        menuItems.add(new TextMenuItem(2, ResourceHandler.getInstance().getFontIndieFlower36(), "Back to menu", vertexBufferObjectManager));

        int posX = 100;
        int posY = 200;

        for (TextMenuItem menuItem : menuItems) {
            menuItem.setPosition(posX, posY);
            posY += 50;
            this.addMenuItem(menuItem);
        }
        this.setOnMenuItemClickListener(this);

        Sprite pauseBtn = new Sprite(700, 0, textureResource.getResource(GamefieldTextureResource.PAUSE_BUTTON), vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown() && !gameScene.hasChildScene())
                    GameManager.getInstance().pauseGame();
                return true;
            }
        };
        pauseBtn.setZIndex(200);
        gameScene.registerTouchArea(pauseBtn);
        gameScene.attachChild(pauseBtn);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 1:
                GameManager.getInstance().startGame();
                break;
            case 2:
                GameManager.getInstance().gameOver();
                break;
        }
        return false;
    }
}
