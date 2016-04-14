package com.topmovies.trakt.service;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.topmovies.BuildConfig;

import java.io.File;
import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public class MovieServiceBuilder {
    private static final String BASE_URL = "https://api-v2launch.trakt.tv";
    private static final long CACHE_SIZE = 1024 * 1024 * 10;

    public static MovieServiceApi create(Context context) {
        final OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(AUTHORIZE_INTERCEPTOR);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.interceptors().add(logging);
        }

        File fileCache = new File(context.getCacheDir(), "movies");
        //noinspection ResultOfMethodCallIgnored
        fileCache.mkdir();
        Cache cache = new Cache(fileCache, CACHE_SIZE);
        client.setCache(cache);

        return new Retrofit.Builder().client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MovieServiceApi.class);
    }

    private static final Interceptor AUTHORIZE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request newRequest;

            newRequest = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", "ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086")
                    .build();
            return chain.proceed(newRequest);
        }
    };
}
