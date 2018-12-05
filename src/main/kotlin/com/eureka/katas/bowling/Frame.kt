package com.eureka.katas.bowling

typealias Frame = List<Int>

fun Frame.isStrike(): Boolean = this.sum() == 10 && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == 10 && this.size == 2