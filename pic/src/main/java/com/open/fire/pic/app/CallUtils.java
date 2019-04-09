package com.open.fire.pic.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * @author shankshu
 * @date 2019/3/30 下午2:12
 */
public class CallUtils {
    public static void callForward(Context context, String phoneNumber) {
        Log.d("hujinhao", "callForward: [context, forwardNumber]" + phoneNumber);
        String uri = "tel:**21*"+ Uri.encode(phoneNumber+"#");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }
}
