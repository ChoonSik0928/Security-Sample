package com.choonsik.security_sample.ui.sample_list

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.ui.model.UISampleModel
import javax.inject.Inject

class SampleListViewModel @Inject constructor() : ViewModel() {
    val sampleList = listOf(
        UISampleModel("Plain text encrypt - decrypt"),
        UISampleModel("PIN CODE encrypt - decrypt"),
        UISampleModel("BioPrompt"),
        UISampleModel("PIN CODE + BioPrompt")
    )
}