package com.choonsik.security_sample.di.module

import com.choonsik.security_sample.di.annotation.ActivityScoped
import com.choonsik.security_sample.ui.MainActivity
import com.choonsik.security_sample.ui.sample_list.SampleListModule
import com.choonsik.security_sample.ui.simple_crypt.SimpleCryptModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            SampleListModule::class,
            SimpleCryptModule::class
        ]
    )
    internal abstract fun getMainActivity(): MainActivity
}