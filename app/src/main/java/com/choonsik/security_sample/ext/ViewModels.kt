package com.choonsik.security_sample.ext

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
