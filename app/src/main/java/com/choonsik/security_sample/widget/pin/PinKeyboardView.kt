package com.choonsik.security_sample.widget.pin

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import com.choonsik.security_sample.R
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.Keyboard
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import com.choonsik.security_sample.widget.pin.keyboard.Row
import com.choonsik.security_sample.widget.pin.util.ArrayUtil.create2DIntArray
import com.choonsik.security_sample.widget.pin.util.ArrayUtil.find2DArrayIndex
import com.choonsik.security_sample.widget.pin.util.ArrayUtil.makeNumberArray0To9WithShuffle

class PinKeyboardView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TableLayout(context, attrs) {

    init {
        var isShuffle = false

        attrs?.run {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.PinAttributes, 0, 0
            )

            isShuffle = typedArray.getBoolean(R.styleable.PinAttributes_isShuffle, false)

            typedArray.recycle()
        }

        val keyboard = createNumericKeyboard(isShuffle)
        setKeyboardView(context, keyboard)
        //넓이 비율 맞추기 위함 row
    }

    fun setKeyboardClickListener(keyboardClickListener: KeyboardClickListener) {
        setPinKeyViewClickListener(keyboardClickListener)
    }

    private fun setPinKeyViewClickListener(keyboardClickListener: KeyboardClickListener) {
        this.children.iterator().forEach {
            if (it is ViewGroup) {
                it.children.iterator().forEach {
                    if (it is PinKeyView) {
                        it.setKeyboardClickListener(keyboardClickListener)
                    }
                }
            }
        }
    }

    private fun setKeyboardView(context: Context, keyboard: Keyboard) {
        keyboard.rows.forEach { row ->
            val tableRow = TableRow(context)
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            params.weight = 1F
            tableRow.layoutParams = params

            row.keys.forEach { pinKey ->
                var pinKeyView = PinKeyView(context)
                when(pinKey){
                    is PinKey.BackKey->{
                        pinKeyView.setKey(pinKey,R.drawable.backspace)
                        pinKeyView.isClickable = true
                    }
                    is PinKey.Num->{
                        pinKeyView.setKey(pinKey)
                        pinKeyView.isClickable = true
                    }

                    is PinKey.EmptyKey->{
                        pinKeyView.isClickable = false
                    }
                }

                tableRow.addView(pinKeyView)
            }

            this.addView(tableRow)
        }
    }

    /**
     * ----------
     * [1][2][3]
     * [4][5][6]
     * [7][8][9]
     * [ ][0][<]
     * ----------
     */
    private fun createNumericKeyboard(isShuffle: Boolean): Keyboard {
        val numberArray = makeNumberArray0To9WithShuffle(isShuffle)

        val rows = arrayListOf<Row>()

        val intArray = create2DIntArray(4, 3)

        intArray.withIndex().forEach { (rowIndex, columnIndex) ->
            val row = Row()
            var key: PinKey = PinKey.EmptyKey
            val keys = arrayListOf<PinKey>()

            columnIndex.withIndex().forEach {
                val index = find2DArrayIndex(rowIndex, columnIndex.size, it.index)
                if (rowIndex == intArray.lastIndex) {
                    // [][number][<] 마지막 row
                    when (it.index) {
                        1 -> {
                            key = PinKey.Num(numberArray.last()) //0
                        }
                        2 -> {
                            key = PinKey.BackKey
                        }
                    }
                    keys.add(key)
                } else {
                    keys.add(PinKey.Num(numberArray[index]))
                }
                row.keys = keys
            }
            rows.add(row)
        }

        return Keyboard(rows)
    }


}