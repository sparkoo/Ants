package cz.sparko.Bugmaze;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

public class GamePause extends CameraScene {
    private int positionX;
    private int positionY;

    private static ITextureRegion pauseBackground;
    private static ITextureRegion pauseIcon;

    public GamePause(Camera camera, final Scene gameScene, VertexBufferObjectManager vertexBufferObjectManager) {
        super(camera);
        positionX = (int)(camera.getWidth() / 2 - pauseBackground.getWidth() / 2);
        positionY = (int)(camera.getHeight() / 2 - pauseBackground.getHeight() / 2);

        final Sprite pausedSprite = new Sprite(positionX, positionY, pauseBackground, vertexBufferObjectManager);
        this.attachChild(pausedSprite);
        this.setBackgroundEnabled(false);

        Sprite pauseBtn = new Sprite(700, 0, pauseIcon, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown() && !gameScene.hasChildScene())
                    gameScene.setChildScene(GamePause.this, false, true, true);
                return true;
            }
        };
        pauseBtn.setZIndex(200);
        gameScene.registerTouchArea(pauseBtn);
        gameScene.attachChild(pauseBtn);
    }

    public static void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        pauseBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, gameActivity, "pauseBackground.png");
        pauseIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, gameActivity, "pauseIcon.png");
    }
}
