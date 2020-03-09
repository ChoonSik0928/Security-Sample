package com.choonsik.security_sample.widget.pin

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import com.choonsik.security_sample.widget.pin.keyboard.Row

class PinKeyboardView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        var isShuffle = false
    }

    /**
     * ----------
     * [1][2][3]
     * [4][5][6]
     * [7][8][9]
     * [ ][0][<]
     * ----------
     */
    fun createNumericKeyboard(isShuffle: Boolean) {

        val rows = arrayListOf<Row>()

        val matrix = createMatrix(3, 4)
        matrix.withIndex().forEach { (rows, columns) ->
            var key: PinKey = PinKey.EmptyKey
            val keys = arrayListOf<PinKey>()

            columns.withIndex().forEach {
                val index = index(rows, columns.size, it.index)
                if (rows == matrix.lastIndex) {
                    when (it.index) {
                        1 -> {

                        }
                        2 -> {

                        }
                        else -> {

                        }
                    }
                } else {

                }
            }

        }
    }

    private fun createMatrix(rows: Int, columns: Int): Array<IntArray> {
        return Array(rows) { IntArray(columns) }
    }

    private fun index(rowIndex: Int, columnSize: Int, columnIndex: Int) =
        (rowIndex * columnSize) + columnIndex
}