package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Helper.Direction;
import cz.sparko.Bugmaze.Manager.GameManager;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public abstract class Block extends AnimatedSprite {
    public static final int SIZE = 64;
    public static final int Z_INDEX = 10;

    protected float centerX;
    protected float centerY;

    protected Coordinate coordinate;
    protected ArrayList<Direction> sourceWays;
    protected ArrayList<Direction> outWays;
    protected int wayNo = 0;

    protected int walkThroughs;

    protected boolean active = false;
    protected boolean deleted = false;

    protected int rotateCount = 0;

    public Block(Coordinate coordinate, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager vertexBufferObjectManager, int walkThroughs) {
        super(0, 0, pTiledTextureRegion, vertexBufferObjectManager);
        sourceWays = new ArrayList<Direction>();
        outWays = new ArrayList<Direction>();
        this.coordinate = coordinate;
        setPossibleSourceWays();
        setOutWays();
        setZIndex(Z_INDEX);
        this.walkThroughs = walkThroughs;
    }

    public boolean isActive() {  return this.active; }
    public void activate() { setActive(true); }
    public void deactivate() { setActive(false); }
    public void setActive(boolean active) { this.active = active; }

    public boolean isDeleted() {
        return this.deleted;
    }

    public boolean delete() {
        if (walkThroughs <= 1) {
            this.deleted = true;
            this.active = false;

            this.registerEntityModifier(new DelayModifier(GameManager.getInstance().getCharacter().getSpeed(), new IEntityModifier.IEntityModifierListener() {
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

    public static Block createStartBlock(Coordinate coordinate, Game game) {
        Block nBlock = new Start(coordinate, game);
        for (int i = 0; i < new Random().nextInt(4); i++)
            nBlock.rotate();
        return nBlock;
    }

    public static Block createRandomBlock(Class[] blocks, float probabilities[], int walkThroughs[], Game game, Coordinate coordinate, boolean randomRotate) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Random randomGenerator = new Random();
        float sumOfProbabilities = 0;
        float pickBlock = randomGenerator.nextFloat();
        int pickedBlockIndex;
        for (pickedBlockIndex = 0; pickedBlockIndex < probabilities.length; pickedBlockIndex++ ) {
            if (sumOfProbabilities + probabilities[pickedBlockIndex] >= pickBlock)
                break;
            sumOfProbabilities += probabilities[pickedBlockIndex];
        }

        Constructor<Block> constructor = blocks[pickedBlockIndex].getConstructor(Coordinate.class, Game.class, int.class);
        Block nBlock = constructor.newInstance(coordinate, game, walkThroughs[pickedBlockIndex]);

        if (randomRotate)
            for (int i = 0; i < randomGenerator.nextInt(4); i++)
                nBlock.rotate();

        return nBlock;
    }

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        if (!deleted && pSceneTouchEvent.isActionDown() && !collidesWith(GameManager.getInstance().getCharacter())) {
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

        if (Math.abs(directionX) > 1 || Math.abs(directionY) > 1) GameManager.getInstance().needRefreshField(); //refresh field on walk through wall

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

    //TODO: anticlockwise rotate
    public void rotate() {
        this.setCurrentTileIndex((this.getCurrentTileIndex() + 1) % (this.getTileCount() / 2));
        for (int i = 0; i < outWays.size(); i++)
            outWays.set(i, Direction.fromInt((outWays.get(i).getValue() + 1) % 4));
        for (int i = 0; i < sourceWays.size(); i++)
            sourceWays.set(i, Direction.fromInt((sourceWays.get(i).getValue() + 1) % 4));

        rotateCount = ++rotateCount % 4;
    }


    @Override
    public void setPosition(float pX, float pY) {
        super.setPosition(pX, pY);
        centerX = this.getX() + (SIZE / 2) - (Character.SIZE_X / 2);
        centerY = this.getY() + (SIZE / 2) - (Character.SIZE_Y / 2);
    }

    public abstract SequenceEntityModifier getMoveHandler(Character character);
    public abstract void setPossibleSourceWays();
    public abstract void setOutWays();
}
