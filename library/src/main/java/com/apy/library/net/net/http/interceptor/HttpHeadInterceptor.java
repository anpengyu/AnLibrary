package com.apy.library.net.net.http.interceptor;

import com.apy.library.net.net.http.RequestData;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * JohnRambo on 2017/4/11 17:43
 */
public class HttpHeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request build = chain.request().newBuilder()
                .addHeader("token", RequestData.INSTANCE.getToken())
                /*.addHeader("TIMESTAMP", getTimestamp())//Timestamp
                .addHeader("TOKEN", getToken())//Token
                .addHeader("ACCESS", getAccessToken())//Access_token
                .addHeader("UID", getUID())//Uid
                .addHeader("UUID", TextUtils.isEmpty(getUUID()) ? "123456789" : getUUID())//Uuid
                .addHeader("PLATFORM", "Android")//Platform
                .addHeader("VERSION", VERSIONCODE)//version
                .addHeader("APPVERSION", appVersion)//AppVersion*/
                .build();
        return chain.proceed(build);
    }
}
