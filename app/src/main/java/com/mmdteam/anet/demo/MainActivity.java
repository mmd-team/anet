package com.mmdteam.anet.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mmdteam.anet.lib.ServiceGenerator;

import io.reactivex.observers.DisposableObserver;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.mmdteam.anet.lib.SubscribeUtils.toSubscribe;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getCanonicalName();
  private DemoService service;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.hello_world)
        .setOnClickListener(
            v -> {
              //
              // ServiceGenerator.getInstance("http://gank.io/api/").setLogging(HttpLoggingInterceptor.Level.BODY);
              toSubscribe(
                  service.today(),
                  new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                      Log.d(TAG, "onNext: " + o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                      Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                      Log.d(TAG, "onComplete: ");
                    }
                  });
            });

    service =
        ServiceGenerator.getInstance("http://gank.io/api/", HttpLoggingInterceptor.Level.BODY)
            .createService(DemoService.class);
    toSubscribe(
        service.today(),
        new DisposableObserver<Object>() {
          @Override
          public void onNext(Object o) {
            Log.d(TAG, "onNext: " + o.toString());
          }

          @Override
          public void onError(Throwable e) {
            Log.d(TAG, "onError: " + e.getMessage());
          }

          @Override
          public void onComplete() {
            Log.d(TAG, "onComplete: ");
          }
        });
  }
}
