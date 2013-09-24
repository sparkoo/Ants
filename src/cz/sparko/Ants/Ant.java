package cz.sparko.Ants;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ant extends AnimatedSprite {

    public static final int SIZE_X = 16;
    public static final int SIZE_Y = 16;


    public Ant(float pX, float pY, final TiledTextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
    }
}
