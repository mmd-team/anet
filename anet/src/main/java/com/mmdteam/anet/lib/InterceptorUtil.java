package com.mmdteam.anet.lib;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

class InterceptorUtil {
  //    private static final String TAG = InterceptorUtil.class.getSimpleName();

  static Interceptor headerInterceptor() {
    return chain -> {
      Request original = chain.request();
      Request.Builder builder = original.newBuilder();
      Request request = builder.build();
      return chain.proceed(request);
    };
  }

  /**
   * loggingInterceptor
   *
   * @return loggingInterceptor
   */
  static HttpLoggingInterceptor loggingInterceptor(HttpLoggingInterceptor.Level level) {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(level);
    return loggingInterceptor;
  }

  @SuppressWarnings("MismatchedReadAndWriteOfArray")
  private static String[] VERIFY_HOST_NAME_ARRAY = new String[] {};

  static HostnameVerifier hostnameVerifier() {
    return (hostname, session) -> {
      if (TextUtils.isEmpty(hostname)) {
        return false;
      }
      return !Arrays.asList(VERIFY_HOST_NAME_ARRAY).contains(hostname);
    };
  }

  static SSLSocketFactory sslSocketFactory() {
    TrustManager[] trustAllCerts =
        new TrustManager[] {
          new X509TrustManager() {
            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {}

            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[] {};
            }
          }
        };
    SSLSocketFactory sslSocketFactory = null;
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new SecureRandom());
      sslSocketFactory = sslContext.getSocketFactory();
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      e.printStackTrace();
    }
    return sslSocketFactory;
  }

  static X509TrustManager x509TrustManager() {
    return new X509TrustManager() {
      @SuppressLint("TrustAllX509TrustManager")
      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) {}

      @SuppressLint("TrustAllX509TrustManager")
      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) {}

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[] {};
      }
    };
  }
}
