package me.jessyan.mvparms.demo.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import org.json.JSONObject;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-09-25
 */
public interface GirlContract {
    interface View extends IView{

    }

    interface Model extends IModel{
        io.reactivex.Observable<JSONObject> getImageList();
    }


}
