package com.crackncrunch.amanim.di.modules;

import com.crackncrunch.amanim.data.managers.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule extends FlavorModelModule{
    @Provides
    @Singleton
    DataManager privateDataManager() {
        return DataManager.getInstance();
    }
}
