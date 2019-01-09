package com.open.fire.pic.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.open.fire.pic.mvp.contract.HomeContract;
import com.open.fire.pic.mvp.model.entity.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-11-26
 */
public abstract class MainModel extends BaseModel implements HomeContract.Model {


    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }



    @Override
    public void onDestroy() {

    }
}
