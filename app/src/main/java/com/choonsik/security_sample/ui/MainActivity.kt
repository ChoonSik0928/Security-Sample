package com.choonsik.security_sample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.choonsik.security_sample.R
import com.choonsik.security_sample.databinding.ActivityMainBinding
import com.choonsik.security_sample.extension.assistedActivityViewModels
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel by assistedActivityViewModels<MainActivityViewModel> { viewModelFactory }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
