package com.crackncrunch.amanim.di.modules;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.crackncrunch.amanim.App;
import com.crackncrunch.amanim.utils.AppConfig;

import dagger.Module;
import dagger.Provides;

@Module
public class FlavorModelModule {

    @Provides
    JobManager provideJobManager() {
        Configuration configuration = new Configuration.Builder(App.getContext())
                .minConsumerCount(AppConfig.MIN_CONSUMER_COUNT) // always keep at least one consumer alive
                .maxConsumerCount(AppConfig.MAX_CONSUMER_COUNT) // up to 3 consumers at a time
                .loadFactor(AppConfig.LOAD_FACTOR) // 3 jobs per consumer
                .consumerKeepAlive(AppConfig.KEEP_ALIVE) // keep alive 2 min if thread has no consumer
                .build();
        return new JobManager(configuration);
    }
}
