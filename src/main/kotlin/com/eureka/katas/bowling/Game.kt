package com.eureka.katas.bowling

class Game {

    var rolls = mutableListOf<Int>()

    fun score(): Int {
        return rolls.sum()
    }

    fun roll(pinsDown: Int) {
        rolls.add(pinsDown)
    }
}