package com.choonsik.security_sample.widget.pin.keyboard

data class Row(var keys: ArrayList<PinKey> = arrayListOf())

data class Keyboard(var rows: ArrayList<Row>)