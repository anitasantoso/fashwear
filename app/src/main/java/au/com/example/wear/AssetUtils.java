package au.com.example.wear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

final class AssetUtils {
    private static final String TAG = "RecipeAssistant";

    public static byte[] loadAsset(Context context, String asset) {
        byte[] buffer = null;
        try {
            InputStream is = context.getAssets().open(asset);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to load asset " + asset + ": " + e);
        }
        return buffer;
    }

    public static JSONObject loadJSONAsset(Context context, String asset) {
        String jsonString = new String(loadAsset(context, asset));
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON asset " + asset + ": " + e);
        }
        return jsonObject;
    }

    public static Bitmap loadBitmapAsset(Context context, String asset) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = context.getAssets().open(asset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (is != null) {
            bitmap = BitmapFactory.decodeStream(is);
        }
        return bitmap;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}

