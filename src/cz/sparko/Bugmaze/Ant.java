package cz.sparko.Bugmaze;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ant extends AnimatedSprite {

    public static final int SIZE_X = 32;
    public static final int SIZE_Y = 32;

    public static final int Z_INDEX = 100;

    private static final float baseSpeed = 0.9f;

    private float speed = baseSpeed;

    public Ant(float pX, float pY, final TiledTextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.setZIndex(Z_INDEX);
    }

    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
    }

    public void switchSpeed() {
        if (speed == baseSpeed)
            speed = speed / 2;
        else
            speed = baseSpeed;
    }

    public float getSpeed() { return speed; }
}
