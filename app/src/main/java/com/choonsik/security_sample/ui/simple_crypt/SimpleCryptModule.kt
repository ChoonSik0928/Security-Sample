package com.choonsik.security_sample.ui.simple_crypt

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SimpleCryptModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSimpleCryptFragment(): SimpleCryptFragment

    @Binds
    @IntoMap
    @ViewModelKey(SimpleCryptViewModel::class)
    abstract fun bindFragmentSimpleCryptViewModel(viewModel: SimpleCryptViewModel): ViewModel
}