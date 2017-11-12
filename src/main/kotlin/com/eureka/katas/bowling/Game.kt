package com.eureka.katas.bowling

class Game {

    var rolls = mutableListOf<Int>()

    fun score(): Int {
        var total = 0
        rolls.forEachIndexed { index, pinsDown ->
            total += pinsDown
            if (index > 1 && index % 2 == 0 && (rolls[index - 1] + rolls[index - 2] == 10)) {
                total += pinsDown
            }
        }
        return total
    }

    fun roll(pinsDown: Int) {
        rolls.add(pinsDown)
    }
}