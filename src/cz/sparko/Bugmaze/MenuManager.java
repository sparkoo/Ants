package cz.sparko.Bugmaze;

import cz.sparko.Bugmaze.Menu.Menu;

public class MenuManager extends Manager {
    private static MenuManager instance;

    private Menu currentMenu;

    private MenuManager() {}

    public static MenuManager getInstance() {
        if (instance != null)
            return instance;
        instance = new MenuManager();
        return instance;
    }

    public void setCurrentMenu(Menu nMenu) { this.currentMenu = nMenu; }
}
