package com.open.fire.pic.di;

import android.util.Log;

import javax.inject.Inject;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-03
 */
public class Baby {
    @Inject
    public Baby() {
    }


    public String def(){
        Log.d("Baby   -=-", "_def_16_ :\n _[]");
        return "babay";
    }
}
