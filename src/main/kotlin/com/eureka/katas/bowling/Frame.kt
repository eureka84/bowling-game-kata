package com.eureka.katas.bowling

typealias Frame = List<Int>

fun Frame.isStrike(): Boolean = this.sum() == TOTAL_PINS && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == TOTAL_PINS && this.size == MAX_THROWS_PER_FRAME
fun Frame.firstThrow(): PinsKnockedDown = this[0]
fun Frame.pinsKnockedDown(): PinsKnockedDown = this.sum()
fun emptyFrame(): Frame = emptyList()