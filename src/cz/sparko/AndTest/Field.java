package cz.sparko.AndTest;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.LinkedList;

public class Field extends SimpleBaseGameActivity implements IOnSceneTouchListener {
    private static final int CAMERA_WIDTH = 1280;
    private static final int CAMERA_HEIGHT = 768;

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private TiledTextureRegion mFaceTextureRegion;
    private TiledTextureRegion mBlockTextureRegion;

    private Scene mScene;

    private LinkedList<Block> blocks = new LinkedList<Block>();

    public Field() {
    }

    public static int getCameraWidth() {
        return CAMERA_WIDTH;
    }

    public static int getCameraHeight() {
        return CAMERA_HEIGHT;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 42, 32, TextureOptions.BILINEAR);
        this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "brix1.png", 0, 0, 1, 1);
        this.mBitmapTextureAtlas.load();
    }

    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        mScene = new Scene();
        mScene.setBackground(new Background(0.22f, 0.22f, 0.22f));
        mScene.setOnSceneTouchListener(this);

        final float centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
        final Beam beam = new Beam(centerX, centerY, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
        beam.setVelocity(100, 100);

        mScene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                for (Block b : blocks)
                    if (b.collidesWith(beam))
                        b.beamCollide(beam);
            }

            @Override
            public void reset() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        mScene.attachChild(beam);

        return mScene;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        System.out.println("touch x: " + pSceneTouchEvent.getX() + "; y: " + pSceneTouchEvent.getY());
        if (pSceneTouchEvent.isActionDown()) {
            Block nBlock = new Block(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), this.mBlockTextureRegion, this.getVertexBufferObjectManager());
            nBlock.setScale(3);
            nBlock.setRotation(45f);

            pScene.attachChild(nBlock);
            blocks.add(nBlock);
            return true;
        }
        return false;
    }
}
