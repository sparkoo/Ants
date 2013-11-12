package cz.sparko.Bugmaze.PowerUp;

import org.andengine.opengl.texture.region.ITiledTextureRegion;

public abstract class Boost extends PowerUp implements PowerUpNextBlockListener {
    private static final int TIMER = 2;

    private boolean boostRequest = false;
    private boolean boosted = false;
    private int boostedForBlocksCounter = 0;

    protected int blockCount;
    protected float speedMultiplier;

    public Boost(ITiledTextureRegion textureRegion) {
        super(textureRegion);
    }

    @Override
    public void action() {
        gameUpdateHandler.setPowerUpNextBlockListener(this);
        boostRequest = true;
    }

    @Override
    protected int getTimeToRefresh() {
        return TIMER;
    }

    @Override
    public void reachedNextBlock() {
        if (boosted) {
            if (++boostedForBlocksCounter >= blockCount) {
                character.switchSpeed(1f);
                boostedForBlocksCounter = 0;
                boosted = false;
                boostRequest = false;
                gameUpdateHandler.unsetPowerUpNextBlockListener();
            }
        } else if(boostRequest) {
            boosted = true;
            character.switchSpeed(speedMultiplier);
        }
    }
}
