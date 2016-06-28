package com.zitech.appframework2;


import com.zitech.framework.data.network.Constants;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package com.zitech.personalaccount.data.network.service
 * @Description:(用一句话描述该文件做什么)
 * @date 2016/5/13 15:05
 */
public interface AccountService {


    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @GET(Constants.GET_USER_INFO)
    @Headers(RetrofitClient.CONTENT_TYPE + RetrofitClient.JSON)
    Observable<ApiResponse<UserInfo>> getUserInfo(@Query("access_token") String token);



}
