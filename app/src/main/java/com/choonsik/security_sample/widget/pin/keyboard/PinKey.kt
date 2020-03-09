package com.choonsik.security_sample.widget.pin.keyboard

import java.lang.IllegalArgumentException

sealed class PinKey {
    class Num(val value: Int) : PinKey()
    class Alphabet(val value: Char) : PinKey()
    object BackKey : PinKey()
    object EmptyKey : PinKey()

    fun getKey(pinKey: PinKey): String {
        when (pinKey) {
            is Num -> {
                if (pinKey.value in 0..9) {
                    return pinKey.value.toString()
                } else {
                    throw IllegalArgumentException("pinKey supported only 0 to 9")
                }
            }

            is Alphabet -> {
                if (pinKey.value in 'a'..'z' || pinKey.value in 'A'..'Z') {
                    return pinKey.value.toString()
                } else {
                    throw IllegalArgumentException("pinKey supported only a to Z ")
                }
            }
            is BackKey -> {
                return "<-"
            }
            is EmptyKey -> {
                return ""
            }

            else -> return ""
        }
    }
}