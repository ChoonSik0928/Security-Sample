package com.choonsik.security_sample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.choonsik.security_sample.R
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var result = arrayListOf<String>()
        pin_keyboard.setKeyboardClickListener(object : KeyboardClickListener {
            override fun onKeyClick(key: PinKey) {

                when (key) {
                    is PinKey.BackKey -> {
                        if(result.isNotEmpty()){
                            result.removeAt(result.lastIndex)
                        }
                    }

                    is PinKey.Alphabet,
                    is PinKey.Num -> {
                        result.add(PinKey.getString(key))
                    }
                }

                tv_result.text = result.toString()
            }
        })
    }
}
