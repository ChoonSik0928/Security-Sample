package com.choonsik.security_sample.widget.pin

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.choonsik.security_sample.R
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.Keyboard
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import com.choonsik.security_sample.widget.pin.keyboard.Row
import com.choonsik.security_sample.widget.pin.util.ArrayUtil
import com.choonsik.security_sample.widget.pin.util.ArrayUtil.create2DIntArray
import com.choonsik.security_sample.widget.pin.util.ArrayUtil.find2DArrayIndex
import com.choonsik.security_sample.widget.pin.util.ArrayUtil.makeNumberArray0To9WithShuffle

class PinKeyboardView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TableLayout(context, attrs) {
    enum class PinType(val value: Int) {
        NUMERIC(0), ALPHABET(1);

        companion object {
            fun fromInt(value: Int) = values().first { it.ordinal == value }
        }
    }

    init {
        var isShuffle = false
        var pinType = PinType.NUMERIC

        attrs?.run {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.PinAttributes, 0, 0
            )

            isShuffle = typedArray.getBoolean(R.styleable.PinAttributes_isShuffle, false)

            pinType = PinType.fromInt(
                typedArray.getInt(
                    R.styleable.PinAttributes_pinType,
                    PinType.NUMERIC.value
                )
            )

            typedArray.recycle()
        }

        val keyboard =
            when (pinType) {
                PinType.NUMERIC -> {
                    createNumericKeyboard(isShuffle)
                }
                PinType.ALPHABET -> {
                    createAlphabetKeyboard(isShuffle)
                }
            }
        setKeyboardView(context, keyboard, pinType)
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

    private fun setKeyboardView(context: Context, keyboard: Keyboard, pintType: PinType) {
        keyboard.rows.forEach { row ->
            val tableRow = TableRow(context)
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            params.weight = 1F
            tableRow.layoutParams = params

            if(pintType == PinType.ALPHABET){
                if(keyboard.rows.indexOf(row) == keyboard.rows.lastIndex){
                    tableRow.addView(optionalBlankViewForRatio())
                }
            }

            row.keys.forEach { pinKey ->
                var pinKeyView = PinKeyView(context)

                when (pinKey) {
                    is PinKey.BackKey -> {
                        pinKeyView.setKey(pinKey, R.drawable.backspace)
                        pinKeyView.isClickable = true
                    }
                    is PinKey.Alphabet,
                    is PinKey.Num -> {
                        pinKeyView.setKey(pinKey)
                        pinKeyView.isClickable = true
                    }

                    is PinKey.EmptyKey -> {
                        pinKeyView.isClickable = false
                    }
                }

                tableRow.addView(pinKeyView)
            }
            if(pintType == PinType.ALPHABET){
                if(keyboard.rows.indexOf(row) == keyboard.rows.lastIndex){
                    tableRow.addView(optionalBlankViewForRatio())
                }
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

    /**
     * [A][B][C][D][E][F][G]
     * [H][I][J][K][L][M][N]
     * [O][P][Q][R][S][T][U]
     * [V][W][X][Y][Z][<]
     */
    private fun createAlphabetKeyboard(isShuffle: Boolean): Keyboard {
        val alphabetArray = ArrayUtil.makeAlphabetArrayAToZWithShuffle(isShuffle)
        val rows = arrayListOf<Row>()
        val intArray = ArrayUtil.create2DIntArray(4, 7)

        intArray.withIndex().forEach { (rowIndex, columnIndex) ->
            val row = Row()
            var key: PinKey = PinKey.EmptyKey
            val keys = arrayListOf<PinKey>()

            columnIndex.withIndex().forEach {
                val index = ArrayUtil.find2DArrayIndex(rowIndex, columnIndex.size, it.index)
                when {
                    index <= alphabetArray.lastIndex -> {
                        key = PinKey.Alphabet(alphabetArray[index])
                        keys.add(key)
                    }
                    index == alphabetArray.lastIndex + 1 -> {
                        key = PinKey.BackKey
                        keys.add(key)
                    }
                }

                row.keys = keys
            }
            rows.add(row)
        }

        return Keyboard(rows)
    }

    private fun optionalBlankViewForRatio(): View {
        val view = PinKeyView(context)
        val params = TableRow.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        params.weight = 0.5f
        view.layoutParams = params
        return view
    }
}