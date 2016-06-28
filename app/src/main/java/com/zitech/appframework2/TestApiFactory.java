package com.zitech.appframework2;

import com.zitech.framework.data.network.ApiFactory;
import com.zitech.framework.data.network.HttpResultFunc;
import com.zitech.framework.data.network.subscribe.SchedulersCompat;

import rx.Observable;

/**
 * 使用 {@link MethodsGenerator}生成代码
 * @Project: AppFramework2
 * @Package: com.zitech.appframework2
 * @Description: (用一句话描述该文件做什么)
 * @author: makk
 * @date: 2016/6/13 16:40
 * @version: V1.0
 */
public class TestApiFactory extends ApiFactory {

    private static AccountService getAccountService() {
        return getService(AccountService.class);
    }

    public static Observable<UserInfo> getUserInfo(String token) {
        return getAccountService().getUserInfo(token).map(new HttpResultFunc<UserInfo>()).compose(SchedulersCompat.<UserInfo>applyExecutorSchedulers());
    }

}
