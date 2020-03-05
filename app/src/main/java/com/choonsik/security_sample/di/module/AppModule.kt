package com.choonsik.security_sample.di.module

import com.choonsik.security_sample.base.DefaultDispatcherProvider
import com.choonsik.security_sample.base.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module
internal interface AppModule {
    @Binds
    fun bindDispatchers(dispatcher: DefaultDispatcherProvider): DispatcherProvider
}