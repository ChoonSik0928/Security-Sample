package com.choonsik.security_sample.widget.pin.util

object ArrayUtil {
    fun makeNumberArray0To9WithShuffle(isShuffle: Boolean): ArrayList<Int> {
        val numberValues = arrayListOf<Int>()
        for (i in 1..9) {
            numberValues.add(i)
        }

        if (isShuffle) {
            numberValues.shuffle()
        }

        numberValues.add(0)

        return numberValues
    }

    fun create2DIntArray(rows: Int, columns: Int): Array<IntArray> {
        return Array(rows) { IntArray(columns) }
    }

    fun find2DArrayIndex(rowIndex: Int, columnSize: Int, columnIndex: Int) =
        (rowIndex * columnSize) + columnIndex
}