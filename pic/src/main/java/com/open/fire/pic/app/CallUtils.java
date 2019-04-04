package com.open.fire.pic.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author shankshu
 * @date 2019/3/30 下午2:12
 */
public class CallUtils {
    public static void callForward(Context context, String forwardNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_CALL,
                Uri.parse(String.format("tel:%s", forwardNumber)));
        dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(dialIntent);
    }
}
