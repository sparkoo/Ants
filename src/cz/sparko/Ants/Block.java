package cz.sparko.Ants;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.Random;

public class Block extends AnimatedSprite {
    public static final int SIZE = 64;
    public boolean walkedThrough = false;

    private static ITiledTextureRegion[] blockTextureRegions;

    public Block(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    public void beamCollide(Ant b) {
        b.setVelocity(b.getVelocityY(), b.getVelocityX());
    }

    public boolean getWalkedThrough() {
        return this.walkedThrough;
    }

    public void walkedThrough() {
        this.walkedThrough = true;
    }

    public static void loadResources(BitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        blockTextureRegions = new ITiledTextureRegion[3];
        blockTextureRegions[0] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "corner.png", 32, 0, 1, 1);
        blockTextureRegions[1] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "cross.png", 96, 0, 1, 1);
        blockTextureRegions[2] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "line.png", 160, 0, 1, 1);
    }


    public static Block createRandomBlockFactory(int x, int y, VertexBufferObjectManager vertexBufferObjectManager) {
        Random rnd = new Random();
        return new Block(x, y, blockTextureRegions[rnd.nextInt(3)], vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    this.setRotation(this.getRotation() + 90);
                    return true;
                }
                return false;
            }
        };
    }
}
