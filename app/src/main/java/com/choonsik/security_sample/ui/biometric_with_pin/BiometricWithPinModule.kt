package com.choonsik.security_sample.ui.biometric_with_pin

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import com.choonsik.security_sample.ui.biometric_with_pin.registration.RegistrationFragment
import com.choonsik.security_sample.ui.biometric_with_pin.registration.RegistrationViewModel
import com.choonsik.security_sample.ui.biometric_with_pin.validation_biometric.ValidationBiometricFragment
import com.choonsik.security_sample.ui.biometric_with_pin.validation_biometric.ValidationBiometricViewModel
import com.choonsik.security_sample.ui.biometric_with_pin.validation_pin.ValidationPinFragment
import com.choonsik.security_sample.ui.biometric_with_pin.validation_pin.ValidationPinViewModel
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
    abstract fun bindBiometricWithPinViewModel(viewModel: BiometricWithPinViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeValidationFragment() : ValidationPinFragment

    @Binds
    @IntoMap
    @ViewModelKey(ValidationPinViewModel::class)
    abstract fun bindValidationViewModel(viewModel: ValidationPinViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment() : RegistrationFragment

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(viewModel: RegistrationViewModel) : ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeValidationBiometricFragment() : ValidationBiometricFragment

    @Binds
    @IntoMap
    @ViewModelKey(ValidationBiometricViewModel::class)
    abstract fun bindValidationBiometricViewModel(viewModel: ValidationBiometricViewModel) : ViewModel
}