package com.choonsik.security_sample.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel() {
    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}