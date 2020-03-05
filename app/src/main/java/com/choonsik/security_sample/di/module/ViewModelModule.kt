package com.choonsik.security_sample.di.module

import androidx.lifecycle.ViewModelProvider
import com.choonsik.security_sample.base.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}