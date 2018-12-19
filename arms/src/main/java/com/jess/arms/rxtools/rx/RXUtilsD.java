package com.jess.arms.rxtools.rx;

import android.util.Log;

import com.jess.arms.mvp.IView;
import com.open.fire.utils_package.qqutils.PreferenceHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-10
 */
public class RXUtilsD {
    DisposableObserver mDisposableObserver;


    public void startCountDown(boolean isSecondEnter, IView iview) {
        //取消订阅
        if (mDisposableObserver != null && !mDisposableObserver.isDisposed()) {
            mDisposableObserver.dispose();
        }

        int leftTime;
        if (isSecondEnter) {
            long lastErroTime = PreferenceHelper.getInstance().getLong("enter_time", 0);
            long nowTime = System.currentTimeMillis();
            if ((nowTime - lastErroTime) > (2 * 60 * 1000)) {
                return;
            } else {
                Long left = (nowTime - lastErroTime);
                left = left / 1000;
                leftTime = left.intValue();
            }
        } else {
            leftTime = 0;
            PreferenceHelper.getInstance().put("enter_time", System.currentTimeMillis());
        }
        final int leftTimeFinal = 120 - leftTime;
        Log.d("XPlay_hujinhao", "leftTimeFinal" + leftTimeFinal);
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .take(leftTimeFinal)  //剩余发射次数
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long increaseTime) throws Exception {

                        return leftTimeFinal - increaseTime.intValue() - 1;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(iview))
                .subscribe(mDisposableObserver);

        mDisposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

    }


    public void flowWorkManager() {
//        按照顺序进行一个数据请求


    }


/*

    private void initData() {

        mWanningLevingDataManager.initWanningViewData()
                .subscribeOn(Schedulers.io()) //这个函数，会把在这之前的代码全部转移到io 线程去
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Integer>bindToLifecycle())
                .subscribe(new ApiSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer == null) {
                            Toast.makeText(XplayWannaLevelingActivity.this, "出错了！", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        if (integer == WanningLevingDataManager.SHOW_WAITING_VIEW) {
                            showWaittingView();
                            OrderInfoData orderedBean = mWanningLevingDataManager.getOrderedBean();

                            fciGameAvator.setImageURI(orderedBean.getOrderGameIcon());
                            tvGameServerArea.setText("" + orderedBean.getServerArea());
                            tvOrderBill.setText(AmountUtil.getAmountDecimalString(orderedBean.getTotalPrice()) + "元");
                            createTime = orderedBean.getCreatedTime();
                            countDown();

                        }
                        if (integer == WanningLevingDataManager.SHOW_ORDERING_VIEW) {
                            showOrderingView();
                            GameTypeBean gameSortBean = mWanningLevingDataManager.getGameTypeBeanList().get(0);
                            fciOrderingAvator.setImageURI(gameSortBean.getIcon());
                            fciGameAvator.setImageURI(gameSortBean.getIcon());
                            tvGameNameHeader.setText(gameSortBean.getName());
                            tvGameServerAreaHeader.setText(gameSortBean.getDescription());
                            tvGameName.setText(gameSortBean.getName());
                            reflushSelectOption(gameSortBean.getId());
                            selectGameId = gameSortBean.getId();

                            stareCountDown(true);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Toast.makeText(XplayWannaLevelingActivity.this, "" + getErrorMessage(e), Toast.LENGTH_SHORT).show();

                    }
                });


    }


    //初始化有些分类选择
    public Observable<Integer> initWanningViewData() {
        return XPlayNetworkManager.getClientApi().getLastLevelingOrder("wait_accept")
                .flatMap(new Function<JSONArray, ObservableSource<JSONArray>>() {
                    @Override
                    public ObservableSource<JSONArray> apply(JSONArray jsonArray) throws Exception {
                        //test
                        if (jsonArray == null || jsonArray.length() == 0) {
                            //并没有上一个单子
//                            return requestGameList();
                            mOrderedBean = null;
                        } else if (jsonArray != null && jsonArray.length() > 0) {
                            JSONObject jsonObject = (JSONObject) jsonArray.opt(0);
                            mOrderedBean = ApiGsonParser.fromJSONObject(jsonObject, OrderInfoData.class);
//                            return Observable.just(SHOW_WAITING_VIEW);

                        }
                        return XPlayNetworkManager.getClientApi().getAceApplyGameListUrl();
//                        return io.reactivex.Observable.error(new AssertionError("数据错误！"));
                    }
                })
                .flatMap(new Function<JSONArray, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(JSONArray jsonArray) throws Exception {
                        if (jsonArray != null && jsonArray.length() > 0) {
                            gameListJSONArray = jsonArray;
                            mGameTypeBeanList = ApiGsonParser.fromJSONArray(jsonArray, GameTypeBean.class);
                        } else {
                            io.reactivex.Observable.error(new ApiException("Data Error"));
                        }

                        if (mOrderedBean == null) {
                            return Observable.just(SHOW_ORDERING_VIEW);
                        } else {
                            for (GameTypeBean gameTypeBean : mGameTypeBeanList) {
                                if (gameTypeBean.getId() == mOrderedBean.getGameId()) {
                                    mOrderedBean.setOrderGameIcon(gameTypeBean.getIcon());
                                    break;
                                }
                            }
                            return Observable.just(SHOW_WAITING_VIEW);
                        }
                    }
                });
    }
    */

}
