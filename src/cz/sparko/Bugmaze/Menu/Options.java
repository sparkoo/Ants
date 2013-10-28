package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Manager.MenuManager;
import cz.sparko.Bugmaze.Resource.MenuTextureResource;
import cz.sparko.Bugmaze.Helper.Settings;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public class Options extends Menu {
    private ArrayList<AnimatedSpriteMenuItem> menuCustom;
    private ArrayList<ITiledTextureRegion> menuCustomTextures;

    public Options(Game game) {
        super(game);
    }

    @Override
    protected void createCustomItems() {
        menuCustom = new ArrayList<AnimatedSpriteMenuItem>(3);
        menuCustom.add(new AnimatedSpriteMenuItem(0, menuCustomTextures.get(0), game.getVertexBufferObjectManager()));
        menuCustom.add(new AnimatedSpriteMenuItem(1, menuCustomTextures.get(0), game.getVertexBufferObjectManager()));
        int yPosition = 150;
        for (IMenuItem menuItem : menuCustom) {
            menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            menuItem.setPosition(440, yPosition);
            yPosition += 70;
            menuScene.addMenuItem(menuItem);
        }
        refresh();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case -1:
                goBack();
                break;
            case 0:
                toogleSettings(Settings.MUSIC);
                MenuManager.getInstance().playOrPauseMusicBySettings();
                break;
            case 1:
                toogleSettings(Settings.EFFECTS);
                break;
        }
        refresh();
        return false;
    }

    private void refresh() {
        if (game.getSettingsBoolean(Settings.MUSIC)) {
            menuCustom.get(0).setCurrentTileIndex(0);
            menuIcons.get(0).setCurrentTileIndex(0);
        } else {
            menuCustom.get(0).setCurrentTileIndex(1);
            menuIcons.get(0).setCurrentTileIndex(1);
        }

        if (game.getSettingsBoolean(Settings.EFFECTS)) {
            menuCustom.get(1).setCurrentTileIndex(0);
            menuIcons.get(1).setCurrentTileIndex(0);
        } else {
            menuCustom.get(1).setCurrentTileIndex(1);
            menuIcons.get(1).setCurrentTileIndex(1);
        }
    }

    private Boolean toogleSettings(Settings key) {
        boolean newValue = !game.getSettingsBoolean(key);
        game.setSettingsBoolean(key, newValue);
        return newValue;
    }

    @Override
    protected void loadResources() {
        super.loadResources();

        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.OPTIONS_MUSIC));
        menuItemsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.OPTIONS_EFFECTS));

        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.OPTIONS_MUSIC_ICON));
        menuIconsTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.OPTIONS_MUSIC_ICON));

        menuCustomTextures = new ArrayList<ITiledTextureRegion>();
        menuCustomTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.OPTIONS_ON_OFF));
        menuCustomTextures.add((ITiledTextureRegion)textureResource.getResource(MenuTextureResource.OPTIONS_ON_OFF));

        headerTexture = textureResource.getResource(MenuTextureResource.MAIN_HEADER);
    }
}
