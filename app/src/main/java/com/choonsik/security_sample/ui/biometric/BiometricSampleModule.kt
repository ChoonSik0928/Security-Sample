package com.choonsik.security_sample.ui.biometric

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class BiometricSampleModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeBiometricSampleFragment(): BiometricSampleFragment

    @Binds
    @IntoMap
    @ViewModelKey(BiometricSampleViewModel::class)
    abstract fun bindFragmentBiometricSampleViewModel(viewModel: BiometricSampleViewModel): ViewModel
}