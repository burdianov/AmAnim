package com.crackncrunch.amanim.di.components;

import android.content.Context;

import com.crackncrunch.amanim.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
