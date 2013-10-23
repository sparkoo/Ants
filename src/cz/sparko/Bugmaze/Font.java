package cz.sparko.Bugmaze;

import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class Font {
    private static Font scoreFont;
    private static Font countDownFont;

    public static void loadResources(BitmapTextureAtlas mBitmapTextureAtlas, BaseGameActivity gameActivity) {
        FontFactory.setAssetBasePath("font/");
    }
}
