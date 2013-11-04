package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Manager.Manager;
import cz.sparko.Bugmaze.Manager.MenuManager;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Resource.TextureResource;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public abstract class Menu implements MenuScene.IOnMenuItemClickListener {
    private final float MENU_SWITCH_SPEED = 0.2f;
    protected final int MENU_SWITCH_NEXT = 1;
    protected final int MENU_SWITCH_PREV = -1;

    protected Menu prev;
    protected MenuScene menuScene = null;
    protected Game game;
    protected ArrayList<ITiledTextureRegion> menuItemsTextures = new ArrayList<ITiledTextureRegion>();
    protected ArrayList<ITiledTextureRegion> menuIconsTextures = new ArrayList<ITiledTextureRegion>();
    protected ArrayList<AnimatedSpriteMenuItem> menuItems;
    protected ArrayList<AnimatedSpriteMenuItem> menuIcons;
    protected Sprite header;

    protected AnimatedSpriteMenuItem backBtn;

    protected ITextureRegion headerTexture;

    protected static ArrayList<Menu> menuList;

    //TODO: do we need this ?
    protected TextureResource textureResource = MenuManager.getInstance().getResourceHandler().getTextureResource(ResourceHandler.MENU_GENERAL);

    public Menu(Game game) {
        this.game = game;
        loadResources();
        if (menuList == null) {
            menuList = new ArrayList<Menu>(3);
            initMenus();
        }
    }

    private void initMenus() {
        menuList.add(MenuEnum.MAIN.getValue(), new Main(game));
        menuList.add(MenuEnum.PLAY.getValue(), new Play(game));
        menuList.add(MenuEnum.OPTIONS.getValue(), new Options(game));
    }

    public static Menu getMenu(MenuEnum menu) {
        return Menu.menuList.get(menu.getValue());
    }

    private void createMenuScene() {
        menuScene = new MenuScene(game.getCamera());
        menuScene.setBackgroundEnabled(false);

        header = new Sprite(180, 30, headerTexture, game.getVertexBufferObjectManager());
        menuScene.attachChild(header);

        if (prev != null) {
            backBtn.setPosition(100, 50);
            menuScene.addMenuItem(backBtn);
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
        MenuManager.getInstance().setCurrentMenu(newMenu);
        this.getMenuScene().registerEntityModifier(new MoveModifier(MENU_SWITCH_SPEED, 0, -Game.CAMERA_WIDTH * direction, 0, 0));
        IUpdateHandler moveMenuHandler = new IUpdateHandler() {
            float timeElapsed = 0;
            @Override
            public void onUpdate(float pSecondsElapsed) {
                if (timeElapsed < MENU_SWITCH_SPEED)
                    timeElapsed += pSecondsElapsed;
                else {
                    game.getScene().clearChildScene();
                    game.getScene().setChildScene(newMenu.getMenuScene());
                    newMenu.getMenuScene().setPosition(Game.CAMERA_WIDTH * direction, 0);
                    newMenu.getMenuScene().registerEntityModifier(new MoveModifier(MENU_SWITCH_SPEED, Game.CAMERA_WIDTH * direction, 0, 0, 0));
                    getMenuScene().unregisterUpdateHandler(this);
                    getMenuScene().setPosition(0, 0);
                }
            }
            @Override
            public void reset() {
            }
        };
        this.getMenuScene().registerUpdateHandler(moveMenuHandler);
    }

    protected void loadResources() {
        textureResource = MenuManager.getInstance().getResourceHandler().getTextureResource(ResourceHandler.MENU_GENERAL);
    }

    protected void setItems() {
        backBtn = new TwoStateMenuButton(-1, (ITiledTextureRegion)Manager.getResourceHandler().getTextureResource(ResourceHandler.MENU_GENERAL).getResource(MenuGeneralTextureResource.BACK), game.getVertexBufferObjectManager());

        menuItems = new ArrayList<AnimatedSpriteMenuItem>(menuItemsTextures.size());
        menuIcons = new ArrayList<AnimatedSpriteMenuItem>(menuItemsTextures.size());
        for (int i = 0; i < menuItemsTextures.size(); i++) {
            menuItems.add(new TwoStateMenuButton(i, menuItemsTextures.get(i), game.getVertexBufferObjectManager()));
            menuIcons.add(new AnimatedSpriteMenuItem(i, menuIconsTextures.get(i), game.getVertexBufferObjectManager()));
        }
    }
    protected abstract void createCustomItems();
}
