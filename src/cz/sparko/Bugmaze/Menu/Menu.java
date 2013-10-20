package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.GameActivity;
import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public abstract class Menu implements MenuScene.IOnMenuItemClickListener {
    private final float MENU_SWITCH_SPEED = 0.2f;
    protected final int MENU_SWITCH_NEXT = 1;
    protected final int MENU_SWITCH_PREV = -1;

    protected Menu prev;
    protected MenuScene menuScene;
    protected MenuActivity menuActivity;
    protected static ArrayList<ITiledTextureRegion> menuItemsTextures;
    protected static ArrayList<ITiledTextureRegion> menuIconsTextures;
    protected static ArrayList<AnimatedSpriteMenuItem> menuItems;
    protected static ArrayList<SpriteMenuItem> menuIcons;

    protected final Color TEXT_COLOR = new Color(0.9f, 0.9f, 0.9f);
    protected final Color TEXT_COLOR_SELECTED = new Color(0.2f, 0.2f, 0.2f);

    public Menu(Menu prev, MenuActivity menuActivity) {
        this.prev = prev;
        this.menuActivity = menuActivity;
        setMenuItems();
        createMenuScene();
    }

    public static void loadMenuResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        Main.loadResources(mBitmapTextureAtlas, gameActivity);
        Options.loadResources(mBitmapTextureAtlas, gameActivity);
        Play.loadResources(mBitmapTextureAtlas, gameActivity);
    }

    private void createMenuScene() {
        menuScene = new MenuScene(menuActivity.getCamera());
        menuScene.setBackgroundEnabled(false);

        int yPosition = 150;
        for (AnimatedSpriteMenuItem menuItem : menuItems) {
            menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            menuItem.setPosition(180, yPosition);
            yPosition += 70;
            menuScene.addMenuItem(menuItem);
        }
        createCustomItems();
        menuScene.setOnMenuItemClickListener(this);
    }

    public MenuScene getMenuScene() {
        return menuScene;
    }

    protected void goToMenu(final Menu newMenu) {
        goToMenu(newMenu, MENU_SWITCH_NEXT);
    }

    public void goBack() {
        if (prev != null) {
            goToMenu(prev, MENU_SWITCH_PREV);
        }
    }

    protected void goToMenu(final Menu newMenu, final int direction) {
        menuActivity.setCurrentMenu(newMenu);
        this.getMenuScene().registerEntityModifier(new MoveModifier(MENU_SWITCH_SPEED, 0, -GameActivity.CAMERA_WIDTH * direction, 0, 0));
        IUpdateHandler moveMenuHandler = new IUpdateHandler() {
            float timeElapsed = 0;
            @Override
            public void onUpdate(float pSecondsElapsed) {
                if (timeElapsed < MENU_SWITCH_SPEED)
                    timeElapsed += pSecondsElapsed;
                else {
                    menuActivity.getScene().clearChildScene();
                    menuActivity.getScene().setChildScene(newMenu.getMenuScene());
                    newMenu.getMenuScene().setPosition(GameActivity.CAMERA_WIDTH * direction, 0);
                    newMenu.getMenuScene().registerEntityModifier(new MoveModifier(MENU_SWITCH_SPEED, GameActivity.CAMERA_WIDTH * direction, 0, 0, 0));
                    getMenuScene().unregisterUpdateHandler(this);
                }
            }
            @Override
            public void reset() {
            }
        };
        this.getMenuScene().registerUpdateHandler(moveMenuHandler);
    }

    protected abstract void setMenuItems();
    protected abstract void createCustomItems();
}
