package com.mmdteam.anet.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final int DEFAULT_CONNECT_TIMEOUT = 30;
    private static final int DEFAULT_WRITE_TIMEOUT = 30;
    private static final int DEFAULT_READ_TIMEOUT = 30;

    private Retrofit retrofit;
    private OkHttpClient httpClient = null;


    private static final Map<String, ServiceGenerator> instanceMap = new HashMap<>();


    private ServiceGenerator(String baseUrl) {
        retrofit = getRetrofit(baseUrl);
    }


    public static ServiceGenerator getInstance(String baseUrl) {
        synchronized (instanceMap) {
            ServiceGenerator instance = instanceMap.get(baseUrl);
            if (null == instance) {
                instance = new ServiceGenerator(baseUrl);
                instanceMap.put(baseUrl, instance);
            }
            return instance;
        }
    }


    private OkHttpClient getOkHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .hostnameVerifier(InterceptorUtil.hostnameVerifier())
                    .sslSocketFactory(InterceptorUtil.sslSocketFactory(), InterceptorUtil.x509TrustManager())
                    .addInterceptor(InterceptorUtil.headerInterceptor())
                    .addInterceptor(InterceptorUtil.loggingInterceptor())
                    .build();
        }
        return httpClient;

    }

    private Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(String.class, new StringConverter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }


    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

}
