package com.crackncrunch.amanim.mvp.models;

import com.birbit.android.jobqueue.JobManager;
import com.crackncrunch.amanim.data.managers.DataManager;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.di.components.DaggerModelComponent;
import com.crackncrunch.amanim.di.components.ModelComponent;
import com.crackncrunch.amanim.di.modules.ModelModule;

import javax.inject.Inject;

public abstract class AbstractModel {

    @Inject
    DataManager mDataManager;
    @Inject
    JobManager mJobManager;

    public AbstractModel() {
        ModelComponent component = DaggerService.getComponent(ModelComponent.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }

    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }
}