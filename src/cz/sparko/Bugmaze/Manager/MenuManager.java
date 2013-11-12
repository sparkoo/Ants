package cz.sparko.Bugmaze.Manager;

import android.view.KeyEvent;
import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Menu.*;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Resource.TextureResource;
import cz.sparko.Bugmaze.Helper.Settings;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

public class MenuManager extends Manager {
    //TODO: we dont want this. Handle instance in Game
    private static MenuManager instance;

    private Menu currentMenu;

    private TextureResource textureResource;

    public static MenuManager createInstance(Game game) {
        instance = new MenuManager(game);
        return instance;
    }

    public static MenuManager getInstance() {
        return instance;
    }

    private MenuManager(Game game) {
        super(game);
        textureResource = game.getResourceHandler().getTextureResource(ResourceHandler.MENU_GENERAL);
        this.game = game;
        currentMenu = new Main(game);
    }

    public void setCurrentMenu(Menu nMenu) {
        this.currentMenu = nMenu;
    }

    public void goToMenu(MenuEnum menu) {
        currentMenu = Menu.menuFactory(menu, game);

        scene.clearChildScene();
        scene.setChildScene(currentMenu.getMenuScene());
    }

    @Override
    protected void setScene() {
        scene = new Scene();
        scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureResource.getResource(MenuGeneralTextureResource.BACKGROUND), game.getVertexBufferObjectManager())));

        currentMenu = Menu.menuFactory(MenuEnum.MAIN, game);
        scene.setChildScene(currentMenu.getMenuScene(true));
        scene.sortChildren();
    }

    @Override
    public void onPause() {
        game.pauseMusic();
    }

    @Override
    public void onResume() {
        playOrPauseMusicBySettings();
        currentMenu.getMenuScene().setPosition(0, 0);
    }

    @Override
    public void onSwitchOff() {
        game.pauseMusic();
    }

    @Override
    public void onSwitchOn() {
        game.setMusic(game.getResourceHandler().getMenuMusic());
        playOrPauseMusicBySettings();
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) currentMenu.goBack();
    }

    public void playOrPauseMusicBySettings() {
        if (game.getSettingsBoolean(Settings.MUSIC))
            game.playMusic();
        else
            game.pauseMusic();
    }
}
