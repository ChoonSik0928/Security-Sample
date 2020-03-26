package com.choonsik.security_sample.extension

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

inline fun <VM : ViewModel> Fragment.assistedViewModels(
    viewModelClass: KClass<VM>,
    crossinline body: () -> ViewModelProvider.Factory
): Lazy<VM> {
    return createViewModelLazy(viewModelClass, { viewModelStore }) { body() }
}

inline fun <reified T : ViewModel> ComponentActivity.assistedActivityViewModels(
    crossinline body: () -> ViewModelProvider.Factory
): Lazy<T> {
    return viewModels { body() }
}
