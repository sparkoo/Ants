package cz.sparko.Ants;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.ArrayList;
import java.util.Random;

public abstract class Block extends AnimatedSprite {
    public static final int SIZE = 64;

    protected static final int LEFT = 0;
    protected static final int RIGHT = 1;
    protected static final int DOWN = 2;
    protected static final int UP = 3;
    protected static final Coordinate[] directions = {new Coordinate(-1, 0),  new Coordinate(1, 0), new Coordinate(0, 1), new Coordinate(0, -1)};

    protected Coordinate coordinate;
    protected ArrayList<Integer> possibleSourceWays;
    protected ArrayList<Integer> outWays;
    protected int wayNo = 0;

    private boolean active = false;
    private boolean deleted = false;

    private static ITiledTextureRegion[] blockTextureRegions;

    public Block(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        this.coordinate = coordinate;
        this.setPossibleSourceWays();
        this.setOutWays();
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive() {
        this.active = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void delete() {
        this.deleted = true;
        this.active = false;
    }

    public Coordinate getCoordinate() { return coordinate; }

    public static void loadResources(BitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        blockTextureRegions = new ITiledTextureRegion[3];
        blockTextureRegions[0] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "corner.png", 32, 0, 1, 1);
        blockTextureRegions[1] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "cross.png", 96, 0, 1, 1);
        blockTextureRegions[2] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "line.png", 160, 0, 1, 1);
    }


    public static Block createRandomBlockFactory(Coordinate coordinate, int posX, int posY, VertexBufferObjectManager vertexBufferObjectManager) throws NotDefinedBlockException{
        Random rnd = new Random();
        Block nBlock;
        switch(rnd.nextInt(3)) {
            case 0:
                nBlock = new BlockCorner(coordinate, posX, posY, blockTextureRegions[0], vertexBufferObjectManager);
                break;
            case 1:
                nBlock = new BlockCross(coordinate, posX, posY, blockTextureRegions[1], vertexBufferObjectManager);
                break;
            case 2:
                nBlock = new BlockLine(coordinate, posX, posY, blockTextureRegions[2], vertexBufferObjectManager);
                break;
            default:
                throw new NotDefinedBlockException();
        }
        return nBlock;
    }

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        if (pSceneTouchEvent.isActionDown()) {
            this.setRotation(this.getRotation() + 90);
            this.rotate();
            return true;
        }
        return false;
    }

    public Coordinate getOutCoordinate() {
        return directions[outWays.get(wayNo)];
    }

    public boolean canGetIn(Coordinate fromCoordinate) {
        Coordinate fromDirection = new Coordinate(fromCoordinate.getX() - coordinate.getX(), fromCoordinate.getY() - coordinate.getY());
        int from = 0;
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].equals(fromDirection))
                from = i;
        }
        wayNo = possibleSourceWays.indexOf(from);
        if (wayNo > 0)
            return true;
        return false;
    }

    public void rotate() {
        for (int i = 0; i < possibleSourceWays.size() && i < outWays.size(); i++) {
            possibleSourceWays.set(i, possibleSourceWays.get(i) + (((int)this.getRotation() / 90) % 4));
            outWays.set(i, outWays.get(i) + (((int)this.getRotation() / 90) % 4));
        }
    }

    public abstract void moveAnt(Ant ant);
    public abstract void pastToNextBlock(Ant ant, Block[][] blocks);
    public abstract void setPossibleSourceWays();
    public abstract void setOutWays();
}
