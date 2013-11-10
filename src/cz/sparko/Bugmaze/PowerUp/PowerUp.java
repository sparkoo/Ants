package cz.sparko.Bugmaze.PowerUp;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.GameField;
import cz.sparko.Bugmaze.GameUpdateHandler;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class PowerUp {
    public static final int Z_INDEX = 100;

    private boolean active = true;

    protected GameField gameField;
    protected GameUpdateHandler gameUpdateHandler;
    protected Character character;
    protected Game game;
    private ITiledTextureRegion textureRegion;
    private AnimatedSprite sprite;

    protected float timer;

    public PowerUp(ITiledTextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public abstract void action();
    protected abstract int getTimeToRefresh();
    private void createSprite(int posX, int posY, VertexBufferObjectManager vertexBufferObjectManager) {
        sprite = new AnimatedSprite(posX, posY, textureRegion, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (active && pSceneTouchEvent.isActionDown()) {
                    action();
                    active = false;
                    startTimer();
                    sprite.setCurrentTileIndex(1);
                    return true;
                }
                if (pSceneTouchEvent.isActionUp()) {
                    sprite.setCurrentTileIndex(0);
                    return true;
                }
                return false;
            }
        };
        sprite.setZIndex(Z_INDEX);
    }

    private void startTimer() {
        timer = 0;
        getSprite().registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                timer += pSecondsElapsed;
                if (timer >= getTimeToRefresh()) {
                    active = true;
                    getSprite().unregisterUpdateHandler(this);
                }
            }

            @Override
            public void reset() {
            }
        });
    }

    public void init(int posX, int posY, GameField gameField, GameUpdateHandler gameUpdateHandler, Character character, VertexBufferObjectManager vertexBufferObjectManager) {
        this.gameField = gameField;
        this.gameUpdateHandler = gameUpdateHandler;
        this.character = character;
        createSprite(posX, posY, vertexBufferObjectManager);
        timer = getTimeToRefresh();
    }

    public AnimatedSprite getSprite() { return sprite; }
}
