package com.choonsik.security_sample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory
@Inject
constructor(private val creator:@JvmSuppressWildcards  Map<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val found = creator.entries.find { modelClass.isAssignableFrom(it.key) }
        val creator = found?.value ?: throw IllegalArgumentException("unknown model class $modelClass")

        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        }catch (e: Exception){
            throw  RuntimeException(e)
        }
    }

}