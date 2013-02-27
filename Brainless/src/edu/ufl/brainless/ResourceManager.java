package edu.ufl.brainless;

import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import edu.ufl.brainless.ResourceManager;

public class ResourceManager {
	private static Context con;
    private static HashMap<Integer,BitmapPair> bitmaps = new HashMap<Integer,BitmapPair>();

    public static void init(Context c) {
        ResourceManager.con = c;
    }

    public static Resources getResources() {
        return ResourceManager.con.getResources();
    }

    /* Returns BitmapPair for given drawable id. Used by
     * getBitmap() and getBitmapFlipped()
     */
    private static BitmapPair getBitmapPair(int drawableid) {
        BitmapPair b = ResourceManager.bitmaps.get(drawableid);
        if (b == null) {
            b = new BitmapPair(drawableid);
            ResourceManager.bitmaps.put(Integer.valueOf(drawableid), b);
        }
        return b;
    }

    public static Bitmap getBitmap(int drawableid) {
        return ResourceManager.getBitmapPair(drawableid).original;
    }

    /* Given a drawable id, stores both the original bitmap representation
     * and a copy which is flipped across the y-axis
     */
    private static class BitmapPair {
        private static BitmapFactory.Options defaultOptions = new BitmapFactory.Options() {{
            inPurgeable = true;
            inInputShareable = true;
        }};
        public final Bitmap original;
        public BitmapPair(int drawableid) {
            this.original = BitmapFactory.decodeResource( ResourceManager.getResources(), drawableid, defaultOptions );
        }
    }
}
