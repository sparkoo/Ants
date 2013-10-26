package cz.sparko.Bugmaze.Manager;

import android.view.KeyEvent;
import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Menu.Main;
import cz.sparko.Bugmaze.Menu.Menu;
import cz.sparko.Bugmaze.Menu.MenuEnum;
import cz.sparko.Bugmaze.Resource.MenuTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Resource.TextureResource;
import cz.sparko.Bugmaze.Helper.Settings;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

public class MenuManager extends Manager {
    private static MenuManager instance;

    private Menu currentMenu;

    private TextureResource textureResource;

    public static MenuManager createInstance(Game game) {
        if (instance == null)
            instance = new MenuManager(game);
        return instance;
    }

    public static MenuManager getInstance() {
        return instance;
    }

    private MenuManager(Game game) {
        super(game);
        textureResource = resourceHandler.getTextureResource(ResourceHandler.MENU);
        this.game = game;
        currentMenu = new Main(game);
    }

    public void setCurrentMenu(Menu nMenu) {
        this.currentMenu = nMenu;
    }

    public void goToMenu(MenuEnum menu) {
        currentMenu = Menu.getMenu(menu);
        scene.clearChildScene();
        scene.setChildScene(currentMenu.getMenuScene());
    }

    @Override
    protected void setScene() {
        scene = new Scene();
        scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureResource.getResource(MenuTextureResource.BACKGROUND), game.getVertexBufferObjectManager())));

        currentMenu = Menu.getMenu(MenuEnum.MAIN);
        scene.setChildScene(currentMenu.getMenuScene());
    }

    @Override
    public void onPause() {
        game.pauseMusic();
    }

    @Override
    public void onResume() {
        playOrPauseMusicBySettings();
    }

    @Override
    public void onSwitchOff() {
        game.pauseMusic();
    }

    @Override
    public void onSwitchOn() {
        game.setMusic(ResourceHandler.getInstance().getMenuMusic());
        playOrPauseMusicBySettings();
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) currentMenu.goBack();
    }

    public void playOrPauseMusicBySettings() {
        if (game.getSettings(Settings.MUSIC))
            game.playMusic();
        else
            game.pauseMusic();
    }
}
