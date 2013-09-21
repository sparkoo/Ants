package cz.sparko.Ants;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

public class Ant extends AnimatedSprite {

    private final PhysicsHandler mPhysicsHandler;
    private final float maxVelocity = 50.0f;

    private float velocityX;
    private float velocityY;

    public Ant(float pX, float pY, final TiledTextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.mPhysicsHandler = new PhysicsHandler(this);
        this.registerUpdateHandler(this.mPhysicsHandler);
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.mPhysicsHandler.setVelocity(velocityX, velocityY);
    }

    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
        if(this.mX < 0 || this.mY < 0 || this.mX + this.getWidth() > Field.getCameraWidth() || this.mY + this.getHeight() > Field.getCameraHeight()) {
            this.setPosition(Field.getCameraWidth() / 2, Field.getCameraHeight() / 2);
            Random rnd = new Random();

            this.setVelocity(rnd.nextFloat() < 0.5 ? rnd.nextFloat() * maxVelocity : -rnd.nextFloat() * maxVelocity, rnd.nextFloat() < 0.5 ? rnd.nextFloat() * maxVelocity : -rnd.nextFloat() * maxVelocity);
        }

        super.onManagedUpdate(pSecondsElapsed);
    }
}
