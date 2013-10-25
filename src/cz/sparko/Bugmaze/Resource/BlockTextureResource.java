package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Game;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class BlockTextureResource extends TextureResource {
    public static final int CORNER = 0;
    public static final int CROSS = 1;
    public static final int LINE = 2;
    public static final int START = 3;

    public BlockTextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        super(textureAtlas, game);
    }

    @Override
    protected void loadResources() {
        textures.add(CORNER, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "corner.png", 4, 2));
        textures.add(CROSS, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "cross.png", 1, 2));
        textures.add(LINE, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "line.png", 2, 2));
        textures.add(START, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, game, "start.png", 4, 2));
    }
}
