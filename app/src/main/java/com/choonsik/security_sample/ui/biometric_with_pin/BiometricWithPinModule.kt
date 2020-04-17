package com.choonsik.security_sample.ui.biometric_with_pin

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import com.choonsik.security_sample.ui.biometric_with_pin.registration.RegistrationFragment
import com.choonsik.security_sample.ui.biometric_with_pin.registration.RegistrationViewModel
import com.choonsik.security_sample.ui.biometric_with_pin.validation.ValidationFragment
import com.choonsik.security_sample.ui.biometric_with_pin.validation.ValidationViewModel
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

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeValidationFragment() : ValidationFragment

    @Binds
    @IntoMap
    @ViewModelKey(ValidationViewModel::class)
    abstract fun bindFragmentValidationViewModel(viewModel: ValidationViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment() : RegistrationFragment

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindsFragmentRegistrationViewModel(viewModel: RegistrationViewModel) : ViewModel
}