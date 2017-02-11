package com.crackncrunch.amanim.di.components;

import com.squareup.picasso.Picasso;
import com.crackncrunch.amanim.di.modules.PicassoCacheModule;
import com.crackncrunch.amanim.di.scopes.RootScope;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = PicassoCacheModule.class)
@RootScope
public interface PicassoComponent {
    Picasso getPicasso();
}
