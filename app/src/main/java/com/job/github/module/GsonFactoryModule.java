package com.job.github.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by slavik on 6/2/18.
 */

@Module
public class GsonFactoryModule {

    @Singleton
    @Provides
    Gson gson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Singleton
    @Provides
    GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }
}
