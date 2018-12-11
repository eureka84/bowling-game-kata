package com.eureka.katas.bowling

typealias PinsKnockedDown = Int
typealias Frame = List<PinsKnockedDown>

fun Frame.isStrike(): Boolean = this.sum() == TOTAL_PINS && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == TOTAL_PINS && this.size == MAX_THROWS_PER_FRAME
fun Frame.firstThrow(): PinsKnockedDown = this.head()
fun Frame.pinsKnockedDown(): PinsKnockedDown = this.sum()
fun emptyFrame(): Frame = listOf()