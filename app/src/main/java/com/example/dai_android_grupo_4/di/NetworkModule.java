package com.example.dai_android_grupo_4.di;


import android.content.Context;
import com.example.dai_android_grupo_4.core.interceptor.AuthInterceptor;
import com.example.dai_android_grupo_4.data.api.ApiService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.File;
import java.util.concurrent.TimeUnit;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    private final String API_URL = "http://10.0.2.2:8080/api/";

    @Provides
    @Singleton
    Cache provideCache(@ApplicationContext Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        File cacheDir = new File(context.getCacheDir(), "http-cache");
        return new Cache(cacheDir, cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, AuthInterceptor authInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .cache(cache)
                .addNetworkInterceptor(chain -> {
                    return chain.proceed(chain.request())
                            .newBuilder()
                            .header("Cache-Control", "public, max-age=60") // Cache por 60 segundos
                            .build();
                })
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
