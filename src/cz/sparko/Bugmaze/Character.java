package cz.sparko.Bugmaze;

import cz.sparko.Bugmaze.Block.Block;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

public class Character extends AnimatedSprite {

    public static final int SIZE_X = 32;
    public static final int SIZE_Y = 32;

    public static final int Z_INDEX = 100;

    private static final float baseSpeed = 0.7f;

    private float speed = baseSpeed;

    private static ITiledTextureRegion texture;

    public Character(float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, texture, pVertexBufferObjectManager);
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

    public static void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        texture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "characterLadybug.png", 1, 1);
    }

    public void setStartPosition(Block activeBlock) {
        this.setPosition(activeBlock.getX() + (Block.SIZE / 2) - (Character.SIZE_X / 2), activeBlock.getY() + (Block.SIZE / 2) - (Character.SIZE_Y / 2));
        this.setRotation(activeBlock.getOutDirection().getDegree());
    }

    public static ITiledTextureRegion getTexture() { return texture; }
}
