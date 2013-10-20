package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.MenuActivity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;

import java.util.ArrayList;

public class Play extends Menu {
    public Play(Menu prev, MenuActivity menuActivity) {
        super(prev, menuActivity);
    }

    @Override
    protected void setMenuItems() {
        menuItems = new ArrayList<IMenuItem>(4);
        menuItems.add(new ColorMenuItemDecorator(new TextMenuItem(0, menuActivity.getMenuFont(), "Endless Maze", menuActivity.getVertexBufferObjectManager()), TEXT_COLOR_SELECTED, TEXT_COLOR));
        menuItems.add(new ColorMenuItemDecorator(new TextMenuItem(1, menuActivity.getMenuFont(), "Adventure", menuActivity.getVertexBufferObjectManager()), TEXT_COLOR_SELECTED, TEXT_COLOR));
        menuItems.add(new ColorMenuItemDecorator(new TextMenuItem(3, menuActivity.getMenuFont(), "Back", menuActivity.getVertexBufferObjectManager()), TEXT_COLOR_SELECTED, TEXT_COLOR));
    }

    @Override
    protected void createCustomItems() {
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 0:
                menuActivity.startGame();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                goBack();
                break;
        }
        return false;
    }
}
