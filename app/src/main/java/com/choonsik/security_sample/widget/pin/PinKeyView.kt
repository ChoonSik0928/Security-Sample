package com.choonsik.security_sample.widget.pin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout
import com.choonsik.security_sample.R
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.PinKey

class PinKeyView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var keyboardClickListener: KeyboardClickListener? = null
    private var key: PinKey = PinKey.EmptyKey

    init {
        LayoutInflater.from(context).inflate(R.layout.view_pin_key, this, true)
        layoutParams = rowLayoutParams()
    }

    fun setKey(key: PinKey) {
        this.key = key
    }

    fun setKeyboardClickListener(keyboardClickListener: KeyboardClickListener) {
        this.keyboardClickListener = keyboardClickListener
    }

    override fun onClick(p0: View?) {
        if (keyboardClickListener == null || key is PinKey.EmptyKey)
            return

        keyboardClickListener!!.onKeyClick(key)
    }

    private fun rowLayoutParams(): TableRow.LayoutParams {
        val params = TableRow.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        params.weight = 1f
        return params
    }

}