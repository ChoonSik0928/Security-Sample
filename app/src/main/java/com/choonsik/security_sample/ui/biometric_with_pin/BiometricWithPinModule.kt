package com.choonsik.security_sample.ui.biometric_with_pin

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class BiometricWithPinModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeBiometricWithPinFragment() : BiometricWithPinFragment

    @Binds
    @IntoMap
    @ViewModelKey(BiometricWithPinViewModel::class)
    abstract fun bindFragmentBiometricWithPinViewModel(viewModel: BiometricWithPinViewModel): ViewModel
}