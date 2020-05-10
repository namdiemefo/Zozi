package com.naemo.zozi.di

import android.app.Application
import android.content.Context
import com.naemo.zozi.db.pref.PrefManager
import com.naemo.zozi.utils.AppUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesAppUtils(): AppUtils {
        return AppUtils()
    }

    @Singleton
    @Provides
    fun providesAppPreferences(context: Context): PrefManager {
        return PrefManager(context)
    }

}