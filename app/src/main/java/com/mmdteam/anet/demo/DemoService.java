package com.mmdteam.anet.demo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DemoService {
    @GET("today")
    Observable<Object> today();
}
