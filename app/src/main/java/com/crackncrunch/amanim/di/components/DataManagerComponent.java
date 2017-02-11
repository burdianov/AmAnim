package com.crackncrunch.amanim.di.components;

import com.crackncrunch.amanim.data.managers.DataManager;
import com.crackncrunch.amanim.di.modules.LocalModule;
import com.crackncrunch.amanim.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class,
        modules = {NetworkModule.class, LocalModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
