package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.GameActivity;
import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public abstract class Menu implements MenuScene.IOnMenuItemClickListener {
    private final float MENU_SWITCH_SPEED = 0.2f;
    protected final int MENU_SWITCH_NEXT = 1;
    protected final int MENU_SWITCH_PREV = -1;

    protected Menu prev;
    protected MenuScene menuScene = null;
    protected MenuActivity menuActivity;
    protected ArrayList<ITiledTextureRegion> menuItemsTextures;
    protected ArrayList<ITiledTextureRegion> menuIconsTextures;
    protected ArrayList<AnimatedSpriteMenuItem> menuItems;
    protected ArrayList<AnimatedSpriteMenuItem> menuIcons;
    protected ITextureRegion headerTexture;
    protected Sprite header;

    protected AnimatedSpriteMenuItem back;
    protected ITiledTextureRegion backTexture;

    protected static ArrayList<Menu> menuList;

    public Menu(MenuActivity menuActivity, BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
        this.menuActivity = menuActivity;
        loadResources(mBitmapTextureAtlas);
    }

    public static void loadMenuResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas, MenuActivity menuActivity) {
        menuList = new ArrayList<Menu>(3);
        menuList.add(0, new Main(menuActivity, mBitmapTextureAtlas));
        menuList.add(1, new Play(menuActivity, mBitmapTextureAtlas));
        menuList.add(2, new Options(menuActivity, mBitmapTextureAtlas));
    }

    public static Menu getMenu(MenuEnum menu) {
        return Menu.menuList.get(menu.getValue());
    }

    private void createMenuScene() {
        menuScene = new MenuScene(menuActivity.getCamera());
        menuScene.setBackgroundEnabled(false);

        header = new Sprite(180, 30, headerTexture, menuActivity.getVertexBufferObjectManager());
        menuScene.attachChild(header);

        if (prev != null) {
            back.setPosition(100, 50);
            menuScene.addMenuItem(back);
        }

        int yPosition = 150;
        for (AnimatedSpriteMenuItem menuItem : menuItems) {
            menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            menuItem.setPosition(180, yPosition);
            yPosition += 70;
            menuScene.addMenuItem(menuItem);
        }

        yPosition = 160;
        for (AnimatedSpriteMenuItem menuItem : menuIcons) {
            menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            menuItem.setPosition(100, yPosition);
            yPosition += 70;
            menuScene.addMenuItem(menuItem);
        }

        createCustomItems();
        menuScene.setOnMenuItemClickListener(this);
    }

    public MenuScene getMenuScene() {
        if (menuScene == null) {
            setItems();
            createMenuScene();
        }
        return menuScene;
    }

    protected void goToMenu(Menu prev, final Menu newMenu) {
        newMenu.setPrev(prev);
        goToMenu(newMenu, MENU_SWITCH_NEXT);
    }

    protected void setPrev(Menu prev) {
        this.prev = prev;
    }

    public void goBack() {
        if (prev != null) {
            goToMenu(prev, MENU_SWITCH_PREV);
        }
    }

    private void goToMenu(final Menu newMenu, final int direction) {
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

    protected void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas) {
        backTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, menuActivity, "backIcon.png", 1, 2);
    }

    protected void setItems() {
        back = new TwoStateMenuButton(-1, backTexture, menuActivity.getVertexBufferObjectManager());

        menuItems = new ArrayList<AnimatedSpriteMenuItem>(menuItemsTextures.size());
        menuIcons = new ArrayList<AnimatedSpriteMenuItem>(menuItemsTextures.size());
        for (int i = 0; i < menuItemsTextures.size(); i++) {
            menuItems.add(new TwoStateMenuButton(i, menuItemsTextures.get(i), menuActivity.getVertexBufferObjectManager()));
            menuIcons.add(new AnimatedSpriteMenuItem(i, menuIconsTextures.get(i), menuActivity.getVertexBufferObjectManager()));
        }
    }
    protected abstract void createCustomItems();
}
