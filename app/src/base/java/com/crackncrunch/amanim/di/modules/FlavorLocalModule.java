package com.crackncrunch.amanim.di.modules;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.crackncrunch.amanim.data.managers.RealmManager;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FlavorLocalModule {
    public static final String TAG = "BASE";

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        Log.e(TAG, "provideRealmManager init: ");
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(RealmInspectorModulesProvider
                        .builder(context).build()).build());
        return new RealmManager();
    }
}
