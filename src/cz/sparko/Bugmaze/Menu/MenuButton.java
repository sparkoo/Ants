package cz.sparko.Bugmaze.Menu;

import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class MenuButton extends AnimatedSpriteMenuItem {
    public MenuButton(int pID, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pID, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    public void onSelected() {
        this.setCurrentTileIndex(1);
    }

    @Override
    public void onUnselected() {
        this.setCurrentTileIndex(0);
    }
}
