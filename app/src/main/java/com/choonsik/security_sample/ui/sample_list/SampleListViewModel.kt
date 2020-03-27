package com.choonsik.security_sample.ui.sample_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.base.BaseViewModel
import com.choonsik.security_sample.common.Event
import com.choonsik.security_sample.ui.model.UISampleModel
import javax.inject.Inject

class SampleListViewModel @Inject constructor() : BaseViewModel() {

    val sampleList = listOf(
        UISampleModel(0,"Plain text encrypt - decrypt"),
        UISampleModel(1,"PIN CODE encrypt - decrypt"),
        UISampleModel(2,"BioPrompt"),
        UISampleModel(3,"PIN CODE + BioPrompt")
    )

    private val _itemEvent = MutableLiveData<Event<UISampleModel>>()
    val itemEvent: LiveData<Event<UISampleModel>> get() = _itemEvent

    fun onClickItem(model: UISampleModel) {
        _itemEvent.value = Event(model)
    }
}