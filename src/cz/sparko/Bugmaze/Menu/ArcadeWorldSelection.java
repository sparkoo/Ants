package cz.sparko.Bugmaze.Menu;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Resource.MenuGeneralTextureResource;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;

import java.util.ArrayList;

public class ArcadeWorldSelection extends Menu {
    private final int ACTIVE_POS_X = 205;
    private final int POS_Y = 150;
    private final int NEXT_POS_X = 572;
    private final float NEXT_SCALE = 0.7f;
    private final float PREV_SCALE = 1.3f;
    private final float ANIMATION_DURATION = 0.2f;


    private ArrayList<IMenuItem> worlds;
    private IMenuItem nextWorldButton;
    private IMenuItem prevWorldButton;
    private int activeWorld;

    public ArcadeWorldSelection(Game game) {
        super(game);
        initWorlds();
        nextWorldButton = new SpriteMenuItem(-2, textureResource.getResource(MenuGeneralTextureResource.NEXT_WORLD_ICON), game.getVertexBufferObjectManager());
        prevWorldButton = new SpriteMenuItem(-3, textureResource.getResource(MenuGeneralTextureResource.PREV_WORLD_ICON), game.getVertexBufferObjectManager());
        activeWorld = 0;
    }

    private void initWorlds() {
        worlds = new ArrayList<IMenuItem>();
        worlds.add(new SpriteMenuItem(0, textureResource.getResource(MenuGeneralTextureResource.WORLD_GRASS), game.getVertexBufferObjectManager()));
        //worlds.add(new SpriteMenuItem(1, textureResource.getResource(MenuGeneralTextureResource.WORLD_LAVA), game.getVertexBufferObjectManager()));
        //worlds.add(new SpriteMenuItem(2, textureResource.getResource(MenuGeneralTextureResource.WORLD_ICE), game.getVertexBufferObjectManager()));
    }

    @Override
    protected void createCustomItems() {
    }

    @Override
    protected void createMenuScene() {
        menuScene = new MenuScene(game.getCamera());
        menuScene.setBackgroundEnabled(false);

        header = new Sprite(180, 30, headerTexture, game.getVertexBufferObjectManager());
        menuScene.attachChild(header);

        backBtn.setPosition(100, 50);
        menuScene.addMenuItem(backBtn);

        for (int i = 0; i < worlds.size(); i++) {
            if (i == activeWorld) {
                worlds.get(i).setPosition(ACTIVE_POS_X, POS_Y);
            } else if (i == activeWorld + 1) {
                worlds.get(i).setPosition(NEXT_POS_X, POS_Y);
                worlds.get(i).setScale(NEXT_SCALE);
            } else {
                worlds.get(i).setPosition(1000, POS_Y);
            }
        }

        prevWorldButton.setPosition(110, 270);
        nextWorldButton.setPosition(465, 270);

        for (IMenuItem world : worlds)
            menuScene.addMenuItem(world);

        menuScene.addMenuItem(prevWorldButton);
        menuScene.addMenuItem(nextWorldButton);
        menuScene.setOnMenuItemClickListener(this);

        checkShowNavButtons();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case -1:
                goBack();
                break;
            case -2:
                moveToNext();
                break;
            case -3:
                moveToPrev();
                break;
        }
        if (pMenuItem.getID() == activeWorld)
            goToMenu(this, Menu.menuFactory(MenuEnum.ADVENTURE_LEVEL_SELECTION, game));
        else if (pMenuItem.getID() == activeWorld + 1)
            moveToNext();



        return false;
    }

    private void moveToNext() {
        if (activeWorld + 1 < worlds.size()) {
            IMenuItem world = worlds.get(activeWorld);
            world.registerEntityModifier(new SequenceEntityModifier(new MoveModifier(ANIMATION_DURATION, world.getX(), -500, POS_Y, POS_Y), new ScaleModifier(0, 0, 0)));
            world.registerEntityModifier(new ScaleModifier(ANIMATION_DURATION, 1f, PREV_SCALE));
            activeWorld ++;
            world = worlds.get(activeWorld);
            world.registerEntityModifier(new MoveModifier(ANIMATION_DURATION, world.getX(), ACTIVE_POS_X, POS_Y, POS_Y));
            world.registerEntityModifier(new ScaleModifier(ANIMATION_DURATION, NEXT_SCALE, 1f));
            if (activeWorld + 1 < worlds.size()) {
                world = worlds.get(activeWorld + 1);
                world.registerEntityModifier(new MoveModifier(ANIMATION_DURATION, 1000, NEXT_POS_X, POS_Y, POS_Y));
                world.setScale(NEXT_SCALE);
            }

            checkShowNavButtons();
        }
    }

    private void moveToPrev() {
        if (activeWorld - 1 >= 0) {
            IMenuItem world = worlds.get(activeWorld);
            world.registerEntityModifier(new MoveModifier(ANIMATION_DURATION, world.getX(), NEXT_POS_X, POS_Y, POS_Y));
            world.registerEntityModifier(new ScaleModifier(ANIMATION_DURATION, 1f, NEXT_SCALE));
            if (activeWorld + 1 < worlds.size()) {
                world = worlds.get(activeWorld + 1);
                world.registerEntityModifier(new MoveModifier(ANIMATION_DURATION, world.getX(), 1000, POS_Y, POS_Y));
            }
            activeWorld --;
            world = worlds.get(activeWorld);
            world.registerEntityModifier(new ScaleModifier(ANIMATION_DURATION, PREV_SCALE, 1f));
            world.registerEntityModifier(new MoveModifier(ANIMATION_DURATION, -500, ACTIVE_POS_X, POS_Y, POS_Y));

            checkShowNavButtons();
        }
    }

    private void checkShowNavButtons() {
        if (activeWorld == 0)
            prevWorldButton.setScale(0);
        else
            prevWorldButton.setScale(1);

        if (activeWorld == worlds.size() - 1)
            nextWorldButton.setScale(0);
        else
            nextWorldButton.setScale(1);

    }

    @Override
    protected void loadResources() {
        super.loadResources();

        headerTexture = textureResource.getResource(MenuGeneralTextureResource.ADVENTURE_HEADER);
    }
}
