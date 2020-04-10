package com.choonsik.security_sample.ui.biometric_with_pin.registration

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class RegistrationModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment() : RegistrationFragment

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindsFragmentRegistrationViewModel(viewModel: RegistrationViewModel) : ViewModel
}