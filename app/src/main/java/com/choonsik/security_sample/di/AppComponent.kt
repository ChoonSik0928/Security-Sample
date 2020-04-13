package com.choonsik.security_sample.di

import com.choonsik.security_sample.SecuritySampleApplication
import com.choonsik.security_sample.di.module.ActivityBindingModule
import com.choonsik.security_sample.di.module.AppModule
import com.choonsik.security_sample.di.module.PreferenceModule
import com.choonsik.security_sample.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        PreferenceModule::class
    ]
)
interface AppComponent : AndroidInjector<SecuritySampleApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: SecuritySampleApplication): AppComponent
    }
}