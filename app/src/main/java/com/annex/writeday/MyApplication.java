package com.annex.writeday;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.annex.writeday.apiservices.PersistentCookieStore;
import com.annex.writeday.apiservices.model.User;
import com.annex.writeday.apiservices.service.APIService;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import io.realm.Realm;
import io.realm.RealmQuery;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class MyApplication extends Application {

    private static final String BASE_URL = "http://hosting.legendstem.ru:8080/WriteDay-1.0-Release/";

    private static final String TAG_RESULT = "result";

    private APIService service;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CookieHandler cookieHandler = new CookieManager(
                new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .addInterceptor(logging)
                .build();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(httpClient)
                        .build();

        service = retrofit.create(APIService.class);


    }

    public APIService getRestClient() {
        return service;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getTagResult() {
        return TAG_RESULT;
    }





    public User getLocalUser(Context ctx) {

        Realm realm = Realm.getInstance(ctx);
        RealmQuery<User> query = realm.where(User.class);
        return query.findFirst();

    }

}