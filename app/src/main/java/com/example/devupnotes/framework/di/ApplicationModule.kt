package com.example.devupnotes.framework.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class ApplicationModule(val app: Application) {
    @Provides
    fun providesApp() = app
}
