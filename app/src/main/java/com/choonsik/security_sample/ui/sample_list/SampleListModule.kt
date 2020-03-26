package com.choonsik.security_sample.ui.sample_list

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.di.annotation.FragmentScoped
import com.choonsik.security_sample.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SampleListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSampleListFragment(): SampleListFragment

    @Binds
    @IntoMap
    @ViewModelKey(SampleListViewModel::class)
    abstract fun bindFragmentSampleListViewModel(viewModel: SampleListViewModel): ViewModel
}
