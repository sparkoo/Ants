package cz.sparko.Bugmaze.Resource;

import cz.sparko.Bugmaze.Activity.Game;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.ArrayList;

public abstract class TextureResource {
    //TODO: change to hashmap/set? ...
    protected ArrayList<ITextureRegion> textures;
    protected BuildableBitmapTextureAtlas textureAtlas;
    protected Game game;

    public TextureResource(BuildableBitmapTextureAtlas textureAtlas, Game game) {
        this.game = game;
        this.textureAtlas = textureAtlas;
        textures = new ArrayList<ITextureRegion>();
        loadResources();
    }
    public ITextureRegion getResource(int resourceIndex) {
        return textures.get(resourceIndex);
    }
    protected abstract void loadResources();
}
