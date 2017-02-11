package com.crackncrunch.amanim.di.modules;

import android.content.Context;

import com.crackncrunch.amanim.data.managers.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule extends FlavorLocalModule {
    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }
}
