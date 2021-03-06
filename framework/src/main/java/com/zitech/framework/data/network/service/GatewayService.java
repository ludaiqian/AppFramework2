package com.zitech.framework.data.network.service;


import com.zitech.framework.data.network.Constants;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.GetwayResponse;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lu on 2016/6/2.
 */
public interface GatewayService {
    /**
     * @param names
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.GATEWAY_TOKEN)
    @Headers("Content-Type:" + RetrofitClient.FORM)
    Observable<GetwayResponse> gatewayLogin(@FieldMap Map<String, String> names);
}
