package cz.sparko.Ants;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ant extends AnimatedSprite {

    public static final int SIZE_X = 32;
    public static final int SIZE_Y = 32;

    public static final int Z_INDEX = 100;

    private static final float baseSpeed = 1;

    private float speed = baseSpeed;

    public Ant(float pX, float pY, final TiledTextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.setZIndex(Z_INDEX);
        this.animate(200);
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

    public void rotate(Direction direction) {
        switch(direction) {
            case RIGHT:
                this.setRotation(90);
                break;
            case DOWN:
                this.setRotation(180);
                break;
            case LEFT:
                this.setRotation(270);
                break;
            case UP:
                this.setRotation(0);
                break;
        }
    }

    public float getRotation(Direction direction) {
        switch(direction) {
            case RIGHT:
                return 90;
            case DOWN:
                return 180;
            case LEFT:
                return 270;
            case UP:
                return 0;
            default:
                return 0;
        }
    }
}
