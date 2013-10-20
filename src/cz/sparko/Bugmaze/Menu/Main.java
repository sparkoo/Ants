package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public class Main extends Menu {
    public Main(MenuActivity menuActivity) {
        super(null, menuActivity);
    }

    @Override
    protected void setMenuItems() {
        menuItems = new ArrayList<IMenuItem>(4);
        menuItems.add(new ColorMenuItemDecorator(new TextMenuItem(0, menuActivity.getMenuFont(), "Play", menuActivity.getVertexBufferObjectManager()), TEXT_COLOR_SELECTED, TEXT_COLOR));
        menuItems.add(new ColorMenuItemDecorator(new TextMenuItem(1, menuActivity.getMenuFont(), "Leaderboard", menuActivity.getVertexBufferObjectManager()), TEXT_COLOR_SELECTED, TEXT_COLOR));
        menuItems.add(new ColorMenuItemDecorator(new TextMenuItem(2, menuActivity.getMenuFont(), "Options", menuActivity.getVertexBufferObjectManager()), TEXT_COLOR_SELECTED, TEXT_COLOR));
        menuItems.add(new ColorMenuItemDecorator(new TextMenuItem(3, menuActivity.getMenuFont(), "Help", menuActivity.getVertexBufferObjectManager()), TEXT_COLOR_SELECTED, TEXT_COLOR));
    }

    @Override
    protected void createCustomItems() {
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 0:
                goToMenu(new Play(this, menuActivity));
                break;
            case 1:
                menuActivity.handleLeaderboard();
                break;
            case 2:
                goToMenu(new Options(this, menuActivity));
                break;
            case 3:
                break;
        }
        return false;
    }
}
