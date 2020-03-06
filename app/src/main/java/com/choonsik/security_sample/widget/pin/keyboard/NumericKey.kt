package com.choonsik.security_sample.widget.pin.keyboard

/**
 * Get the button value (numeric)
 * @return 0-9 values for digits, -1 for clear button
 */
enum class NumericKey(val buttonValue: Int): Key {
    NUMBER_0(0),
    NUMBER_1(1),
    NUMBER_2(2),
    NUMBER_3(3),
    NUMBER_4(4),
    NUMBER_5(5),
    NUMBER_6(6),
    NUMBER_7(7),
    NUMBER_8(8),
    NUMBER_9(9),
    BUTTON_CLEAR(-1);
}