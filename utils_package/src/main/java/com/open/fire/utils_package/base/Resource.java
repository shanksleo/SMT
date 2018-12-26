package com.open.fire.utils_package.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;


public class Resource {
    private static final String TAG = Resource.class.getSimpleName();

    public static final int id(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "id");
    }

    public static int string(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "string");
    }

    public static int color(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "color");
    }

    public static int drawable(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "drawable");
    }

    public static int layout(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "layout");
    }

    public static int dimen(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "dimen");
    }

    public static int style(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "style");
    }

    public static int raw(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "raw");
    }

    public static int anim(Context paramContext, String paramString) {
        return getIdentifier(paramContext, paramString, "anim");
    }

    public static int attr(Context context, String string) {
        return getIdentifier(context, string, "attr");
    }

    public static int styleable(Context context, String string) {
        return getIdentifier(context, string, "styleable");
    }

    public static Drawable getDrawable(Context context, String str) {
        Resources resources = context.getResources();
        int id = drawable(context, str);

        if (id != 0) {
            return resources.getDrawable(id);
        }

        return null;
    }

    public static final String getString(Context context, String str) {
        Resources resources = context.getResources();
        int id = string(context, str);

        if (id != 0) {
            return resources.getString(id);
        }

        return null;
    }

    private static int getIdentifier(Context context, String name, String defType) {
        Resources resources = context.getResources();
        String defPackage = context.getPackageName();
        int identifier = resources.getIdentifier(name, defType, defPackage);
        return getIdentifier(name, defType, identifier);
    }

    private static int getIdentifier(String name, String defType, int identifier) {
        if (identifier == 0) {
            Log.v(TAG, "resource " + name + ", type " + defType + ", undefined.");
        }
        return identifier;
    }
}
