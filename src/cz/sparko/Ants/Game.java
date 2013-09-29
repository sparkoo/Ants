package cz.sparko.Ants;

import android.content.Intent;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.Random;

//TODO: refactor - was renamed from Field. Make new class Field
public class Game extends SimpleBaseGameActivity implements IOnSceneTouchListener {
    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    private static final int FIELD_SIZE_X = 9;
    private static final int FIELD_SIZE_Y = 6;


    private BitmapTextureAtlas mBitmapTextureAtlas;
    private TiledTextureRegion mAntTextRegion;
    private TiledTextureRegion m2xButtonTextureRegion;

    private Scene mScene;

    private Block[][] blocks;
    private Coordinate startBlock;
    private Ant ant;
    private Block activeBlock;
    private AnimatedSprite x2btn;

    private boolean running = true;

    private static Ant staticAnt = null;
    private static boolean refreshField = false;

    public Game() {
    }

    public static int getCameraWidth() {
        return CAMERA_WIDTH;
    }

    public static int getCameraHeight() {
        return CAMERA_HEIGHT;
    }

    public static void needRefreshField() {
        refreshField = true;
    }

    public static Ant getAnt() { return staticAnt; }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
    }

    @Override
    public void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        //TODO: solve better texture handling
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 352, 64, TextureOptions.BILINEAR);
        this.mAntTextRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "ant.png", 0, 0, 1, 1);
        Block.loadResources(this.mBitmapTextureAtlas, this);
        this.m2xButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "x2btn.png", 288, 0, 2, 1);
        this.mBitmapTextureAtlas.load();
    }

    private void createRandomField(Scene mScene) {
        int startX = CAMERA_WIDTH - (FIELD_SIZE_X * Block.SIZE);
        int startY = (CAMERA_HEIGHT - (FIELD_SIZE_Y * Block.SIZE)) / 2;
        blocks = new Block[FIELD_SIZE_X][FIELD_SIZE_Y];
        Random rnd = new Random();
        startBlock = new Coordinate(rnd.nextInt(FIELD_SIZE_X), rnd.nextInt(FIELD_SIZE_Y));
        for (int x = 0; x < FIELD_SIZE_X; x++) {
            for (int y = 0; y < FIELD_SIZE_Y; y++) {
                try {
                    Block nBlock;
                    Coordinate nCoordinate = new Coordinate(x, y);
                    if (!nCoordinate.equals(startBlock)) {
                        nBlock = Block.createRandomBlockFactory(nCoordinate, startX + (x * Block.SIZE), startY + (y * Block.SIZE), this.getVertexBufferObjectManager());
                        mScene.registerTouchArea(nBlock);
                    } else {
                        nBlock = Block.createStartBlockFactory(nCoordinate, startX + (x * Block.SIZE), startY + (y * Block.SIZE), this.getVertexBufferObjectManager());
                    }

                    blocks[x][y] = nBlock;
                    mScene.attachChild(nBlock);
                } catch(NotDefinedBlockException e) {
                    //TODO: some ingelligent catch
                }
            }
        }

    }

    private void refreshField(Scene mScene) {
        int startX = CAMERA_WIDTH - (FIELD_SIZE_X * Block.SIZE);
        int startY = (CAMERA_HEIGHT - (FIELD_SIZE_Y * Block.SIZE)) / 2;

        for (int x = 0; x < FIELD_SIZE_X; x++) {
            for (int y = 0; y < FIELD_SIZE_Y; y++) {
                if (blocks[x][y].isDeleted()) {
                    try {
                        mScene.detachChild(blocks[x][y]);
                        mScene.unregisterTouchArea(blocks[x][y]);
                        Block nBlock = Block.createRandomBlockFactory(new Coordinate(x, y) ,startX + (x * Block.SIZE), startY + (y * Block.SIZE), this.getVertexBufferObjectManager());
                        blocks[x][y] = nBlock;
                        mScene.attachChild(nBlock);
                        mScene.registerTouchArea(nBlock);
                    } catch(NotDefinedBlockException e) {

                    }
                }
            }
        }
        mScene.sortChildren();
    }

    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        mScene = new Scene();
        mScene.setBackground(new Background(0.22f, 0.22f, 0.22f));
        mScene.setOnSceneTouchListener(this);

        final float centerX = (CAMERA_WIDTH - this.mAntTextRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mAntTextRegion.getHeight()) / 2;
        ant = new Ant(centerX, centerY, this.mAntTextRegion, this.getVertexBufferObjectManager());
        staticAnt = ant;

        createRandomField(mScene);

        activeBlock = blocks[startBlock.getX()][startBlock.getY()];
        ant.setPosition(activeBlock.getX() + (Block.SIZE / 2) - (Ant.SIZE_X / 2), activeBlock.getY() + (Block.SIZE / 2) - (Ant.SIZE_Y / 2));

        x2btn = new AnimatedSprite(20, 20, this.m2xButtonTextureRegion, this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    ant.switchSpeed();
                    x2btn.setCurrentTileIndex(x2btn.getCurrentTileIndex() == 0 ? 1 : 0);
                    return true;
                }
                return false;
            }
        };
        x2btn.setScale(2);

        mScene.setTouchAreaBindingOnActionDownEnabled(true);

        mScene.registerUpdateHandler(new IUpdateHandler() {
            float timeCounter = 0;
            @Override
            public void onUpdate(float pSecondsElapsed) {
                if (refreshField) {
                    refreshField(mScene);
                    refreshField = false;
                }
                if (running && timeCounter > ant.getSpeed()) {
                    timeCounter = 0;
                    int currentX = activeBlock.getCoordinate().getX() + activeBlock.getOutCoordinate().getX();
                    int currentY = activeBlock.getCoordinate().getY() + activeBlock.getOutCoordinate().getY();
                    if (currentX < 0) currentX = FIELD_SIZE_X - 1;
                    if (currentX >= FIELD_SIZE_X) currentX = 0;
                    if (currentY < 0) currentY = FIELD_SIZE_Y - 1;
                    if (currentY >= FIELD_SIZE_Y) currentY = 0;
                    Coordinate nCoordinate = new Coordinate(currentX, currentY);
                    if (blocks[nCoordinate.getX()][nCoordinate.getY()].canGetInFrom(activeBlock.getCoordinate())) {
                        activeBlock = blocks[nCoordinate.getX()][nCoordinate.getY()];
                        ant.registerEntityModifier(activeBlock.getMoveHandler(ant));
                        activeBlock.delete();
                        System.out.println("get in");
                    } else {
                        reset();
                    }
                } else {
                    timeCounter += pSecondsElapsed;
                }
            }

            @Override
            public void reset() {
                Game.this.startActivity(new Intent(Game.this, Menu.class));
                Game.this.finish();
            }
        });
        mScene.registerTouchArea(x2btn);
        mScene.attachChild(x2btn);
        mScene.attachChild(ant);

        return mScene;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        System.out.println("touch x: " + pSceneTouchEvent.getX() + "; y: " + pSceneTouchEvent.getY());
        /*if (pSceneTouchEvent.isActionUp())
            ant.registerEntityModifier(new MoveModifier(0.5f, ant.getX(), pSceneTouchEvent.getX(), ant.getY(), pSceneTouchEvent.getY()));*/
        return false;
    }
}
