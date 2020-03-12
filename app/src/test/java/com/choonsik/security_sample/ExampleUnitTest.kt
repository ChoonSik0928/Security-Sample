package com.choonsik.security_sample

import android.util.Log
import com.choonsik.security_sample.widget.pin.PinKeyboardView
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import com.choonsik.security_sample.widget.pin.keyboard.Row
import com.choonsik.security_sample.widget.pin.util.ArrayUtil
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    companion object {
        /**
         * [1][2][3]
         * [4][5][6]
         * [7][8][9]
         * [ ][0][<]
         */
        private val numericKeyResult = arrayListOf<Row>(
            Row(arrayListOf<PinKey>(PinKey.Num(1), PinKey.Num(2), PinKey.Num(3))),
            Row(arrayListOf<PinKey>(PinKey.Num(4), PinKey.Num(5), PinKey.Num(6))),
            Row(arrayListOf<PinKey>(PinKey.Num(7), PinKey.Num(8), PinKey.Num(9))),
            Row(arrayListOf<PinKey>(PinKey.EmptyKey, PinKey.Num(0), PinKey.BackKey))
        )
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun numericKeyboard() {
        val numberArray = ArrayUtil.makeNumberArray0To9WithShuffle(false)

        val rows = arrayListOf<Row>()
        val intArray = ArrayUtil.create2DIntArray(4, 3)

        intArray.withIndex().forEach { (rowIndex, columnIndex) ->
            val row = Row()
            var key: PinKey = PinKey.EmptyKey
            val keys = arrayListOf<PinKey>()

            columnIndex.withIndex().forEach {
                val index = ArrayUtil.find2DArrayIndex(rowIndex, columnIndex.size, it.index)
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

        rows.forEach {
            it.keys.forEach { pinKey ->
                print("[${PinKey.getString(pinKey)}] ")
            }
            println()
        }

    }

    @Test
    fun alphabetKeyboard() {
        val alphabetArray = ArrayUtil.makeAlphabetArrayAToZWithShuffle(false)
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

        rows.forEach {
            it.keys.forEach { pinKey ->
                print("[${PinKey.getString(pinKey)}] ")
            }
            println()
        }

    }

}
