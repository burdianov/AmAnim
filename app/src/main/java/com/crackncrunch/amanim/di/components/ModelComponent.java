package com.crackncrunch.amanim.di.components;

import com.crackncrunch.amanim.di.modules.ModelModule;
import com.crackncrunch.amanim.mvp.models.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
