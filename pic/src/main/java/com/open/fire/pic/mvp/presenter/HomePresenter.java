package com.open.fire.pic.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.open.fire.pic.mvp.contract.HomeContract;

import javax.inject.Inject;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-29
 */
public class HomePresenter extends BasePresenter<HomeContract.Model,HomeContract.View> {
    @Inject
    public HomePresenter() {
        super();
    }
}
