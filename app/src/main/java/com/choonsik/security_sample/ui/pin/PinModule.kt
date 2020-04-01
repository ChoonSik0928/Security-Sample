package com.choonsik.security_sample.ui.pin

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class PinModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSimpleCryptFragment(): PinFragment

    @Binds
    @IntoMap
    @ViewModelKey(PinViewModel::class)
    abstract fun bindFragmentPinViewModel(viewModel: PinViewModel): ViewModel
}