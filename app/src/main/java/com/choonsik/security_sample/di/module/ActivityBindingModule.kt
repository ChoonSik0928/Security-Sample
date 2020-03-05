package com.choonsik.security_sample.di.module

import com.choonsik.security_sample.di.annotation.ActivityScoped
import com.choonsik.security_sample.ui.MainActivity
import dagger.Module

//TODO UI Module 추가
@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    internal abstract fun getMainActivity() : MainActivity
}