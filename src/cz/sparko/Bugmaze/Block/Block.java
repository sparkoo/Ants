package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Character;
import cz.sparko.Bugmaze.Coordinate;
import cz.sparko.Bugmaze.Direction;
import cz.sparko.Bugmaze.GameActivity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.IModifier;

import java.util.ArrayList;
import java.util.Random;

public abstract class Block extends AnimatedSprite {
    public static final int SIZE = 64;
    public static final int Z_INDEX = 10;

    protected Coordinate coordinate;
    protected ArrayList<Direction> sourceWays;
    protected ArrayList<Direction> outWays;
    protected int wayNo = 0;

    private int walkThroughs;

    private boolean active = false;
    private boolean deleted = false;

    private static ITiledTextureRegion[] blockTextureRegions;

    public Block(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int walkThroughs) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        sourceWays = new ArrayList<Direction>();
        outWays = new ArrayList<Direction>();
        this.coordinate = coordinate;
        setPossibleSourceWays();
        setOutWays();
        setZIndex(Z_INDEX);
        this.walkThroughs = walkThroughs;
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

    public boolean delete() {
        if (walkThroughs == 1) {
            this.deleted = true;
            this.active = false;

            this.registerEntityModifier(new DelayModifier(GameActivity.getCharacter().getSpeed(), new IEntityModifier.IEntityModifierListener() {
                @Override
                public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                }

                @Override
                public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                    setCurrentTileIndex(getCurrentTileIndex() + (getTileCount() / 2));
                }
            }));
            walkThroughs = 0;
            return deleted;
        }
        walkThroughs--;
        return deleted;
    }

    public Coordinate getCoordinate() { return coordinate; }

    public static void loadResources(BuildableBitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        blockTextureRegions = new ITiledTextureRegion[4];
        blockTextureRegions[0] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "corner.png", 4, 2);
        blockTextureRegions[1] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "cross.png", 1, 2);
        blockTextureRegions[2] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "line.png", 2, 2);
        blockTextureRegions[3] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, gameActivity, "start.png", 4, 2);
    }


    public static Block createRandomBlockFactory(Coordinate coordinate, int posX, int posY, VertexBufferObjectManager vertexBufferObjectManager) {
        Random rnd = new Random();
        Block nBlock;
        float pickBlock = rnd.nextFloat();
        if (pickBlock < 0.7)
            nBlock = new BlockCorner(coordinate, posX, posY, blockTextureRegions[0], vertexBufferObjectManager, 1);
        else if (pickBlock < 0.9)
            nBlock = new BlockLine(coordinate, posX, posY, blockTextureRegions[2], vertexBufferObjectManager, 1);
        else
            nBlock = new BlockCross(coordinate, posX, posY, blockTextureRegions[1], vertexBufferObjectManager, 2);

        for (int i = 0; i < rnd.nextInt(4); i++)
            nBlock.rotate();

        return nBlock;
    }

    public static Block createStartBlockFactory(Coordinate coordinate, int posX, int posY, VertexBufferObjectManager vertexBufferObjectManager) {
        Block nBlock = new BlockStart(coordinate, posX, posY, blockTextureRegions[3], vertexBufferObjectManager);
        for (int i = 0; i < new Random().nextInt(4); i++) {
            nBlock.rotate();
        }
        return nBlock;
    }

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        if (!deleted && pSceneTouchEvent.isActionDown() && !collidesWith(GameActivity.getCharacter())) {
            this.rotate();
            return true;
        }
        return false;
    }

    public Coordinate getOutCoordinate() {
        return outWays.get(wayNo).getCoordinate();
    }

    public Direction getOutDirection() {
        return outWays.get(wayNo);
    }

    public boolean canGetInFrom(Coordinate fromCoordinate) {
        if (deleted) return false;

        //walking through side walls
        int directionX = fromCoordinate.getX() - coordinate.getX();
        int directionY = fromCoordinate.getY() - coordinate.getY();

        if (Math.abs(directionX) > 1 || Math.abs(directionY) > 1) GameActivity.getGameField().needRefreshField(); //refresh field on walk through wall

        if (directionX < -1) directionX = 1;
        if (directionX > 1) directionX = -1;
        if (directionY < -1) directionY = 1;
        if (directionY > 1) directionY = -1;

        Coordinate fromDirection = new Coordinate(directionX, directionY);
        Direction from = null;
        for (int i = 0; i < Direction.values().length; i++) {
            if (Direction.fromInt(i).getCoordinate().equals(fromDirection)) {
                from = Direction.fromInt(i);
                break;
            }
        }
        if (from == null) return false;
        wayNo = sourceWays.indexOf(from);
        if (wayNo > -1) return true;
        return false;
    }

    public void rotate() {
        this.setCurrentTileIndex((this.getCurrentTileIndex() + 1) % (this.getTileCount() / 2));
        for (int i = 0; i < outWays.size(); i++)
            outWays.set(i, Direction.fromInt((outWays.get(i).getValue() + 1) % 4));
        for (int i = 0; i < sourceWays.size(); i++)
            sourceWays.set(i, Direction.fromInt((sourceWays.get(i).getValue() + 1) % 4));
    }

    public abstract SequenceEntityModifier getMoveHandler(Character character);
    public abstract void setPossibleSourceWays();
    public abstract void setOutWays();
}
