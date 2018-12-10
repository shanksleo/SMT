package com.jess.arms.zqutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jess.arms.base.ContextProvider;
import com.jess.arms.utils.Preconditions;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-06
 */
public class PreferenceHelper {
    private static PreferenceOperator mPreferenceOperator;


    public PreferenceHelper() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void put(String key,String object){
        Preconditions.checkNotNull(key,"The key could not be null");
        if (mPreferenceOperator == null) {
            getDefault();
        }
        mPreferenceOperator.put(key,object);
    }

    public static PreferenceOperator getInstance() {
        if (mPreferenceOperator == null) {
            getDefault();
        }
        return mPreferenceOperator;
    }



    /*
    * context 的getSharePreference会要求你传入一个name
    * 使用application 的context 它会 使用包名
    * 使用activity 的context  它会使用当前Activity 的名字
    * */
    public static PreferenceOperator getDefault() {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(ContextProvider.get());
        mPreferenceOperator = new PreferenceOperator(sharedPreferences);
        return mPreferenceOperator;
    }


}
