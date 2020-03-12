package com.choonsik.security_sample.widget.pin.util

object ArrayUtil {
    fun makeNumberArray0To9WithShuffle(isShuffle: Boolean): ArrayList<Int> {
        val numberValues = arrayListOf<Int>()
        for (i in 1..9) {
            numberValues.add(i)
        }

        numberValues.add(0)

        if (isShuffle) {
            numberValues.shuffle()
        }

        return numberValues
    }

    fun makeAlphabetArrayAToZWithShuffle(isShuffle: Boolean): ArrayList<Char> {
        val alphabetValues = arrayListOf<Char>()

        for (i in 'A'..'Z') {
            alphabetValues.add(i)
        }

        if (isShuffle) {
            alphabetValues.shuffle()
        }

        return alphabetValues
    }

    fun create2DIntArray(rows: Int, columns: Int): Array<IntArray> {
        return Array(rows) { IntArray(columns) }
    }

    fun find2DArrayIndex(rowIndex: Int, columnSize: Int, columnIndex: Int) =
        (rowIndex * columnSize) + columnIndex
}