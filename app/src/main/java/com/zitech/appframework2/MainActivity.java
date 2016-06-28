package com.zitech.appframework2;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.zitech.framework.data.network.ApiFactory;
import com.zitech.framework.data.network.IContext;
import com.zitech.framework.data.network.response.GetwayResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity implements IContext {

    private Subscriber subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiFactory.gatewayLogin("18268739467", "123456").subscribe(new ProgressSubscriber<GetwayResponse>(this) {
            @Override
            protected void onNextInActive(GetwayResponse getwayResponse) {

            }
        });
        ApiFactory.gatewayLogin("18268739467", "123456").
                doOnNext(new Action1<GetwayResponse>() {
                    @Override
                    public void call(GetwayResponse getwayResponse) {
                        //
                        Constants.ACCESS_TOKEN = getwayResponse.getAccess_token();
                        LogUtils.d("------获取ACCESS_TOKEN成功--------");
                    }
                })
                .subscribe(new Subscriber<GetwayResponse>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtils.d("onError:" + throwable.getClass().getSimpleName() + "" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(GetwayResponse getwayResponse) {
                        LogUtils.d(getwayResponse.toString());
                    }
                });

    }

    private void init() {
        subscriber = new Subscriber<UserInfo>() {

            @Override
            public void onCompleted() {
                LogUtils.d("onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtils.d("onError:" + throwable.getClass().getSimpleName() + "" + throwable.getMessage());
            }

            @Override
            public void onNext(UserInfo userInfo) {
                LogUtils.d("onNext:" + userInfo.toString());
            }
        };
    }

    public void onClick(View view) {
        Observable.just(null)
                .flatMap(new Func1<Object, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(Object o) {
                        return TextUtils.isEmpty(Constants.ACCESS_TOKEN)
                                ? Observable.<UserInfo>error(new NullPointerException("Token is null!"))
                                : TestApiFactory.getUserInfo(Constants.ACCESS_TOKEN);
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                LogUtils.d("retryWhen:" + throwable.getClass().getSimpleName() + "" + throwable.getMessage());
                                if (throwable instanceof Exception) {
                                    //
                                    return ApiFactory.gatewayLogin("18268739467", "123456").
                                            doOnNext(new Action1<GetwayResponse>() {
                                                @Override
                                                public void call(GetwayResponse getwayResponse) {
                                                    //
                                                    Constants.ACCESS_TOKEN = getwayResponse.getAccess_token();
                                                    LogUtils.d("------获取ACCESS_TOKEN成功--------");
                                                }
                                            });
                                }
                                return Observable.just(throwable);
                            }
                        });
                    }
                })
                .subscribe(subscriber);
    }

    @Override
    public boolean isActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !isDestroyed();
        }
        return  !isFinishing();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriber.unsubscribe();
    }
}
