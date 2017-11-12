package com.eureka.katas.bowling

class Game {

    var total: Int = 0

    fun score(): Int {
        return total
    }

    fun roll(pinsDown: Int) {
        total += pinsDown
    }
}