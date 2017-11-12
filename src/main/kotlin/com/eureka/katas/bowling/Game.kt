package com.eureka.katas.bowling

class Game {

    var rolls = mutableListOf<Int>()

    fun score(): Int {
        var total = 0
        rolls.forEachIndexed { index, pinsDown ->
            total += pinsDown
            if (isNotFirstFrame(index) &&
                isFirstThrowOfNextFrame(index) &&
                previousFrameIsSpare(index)
            ) {
                total += pinsDown
            }
        }
        return total
    }

    private fun previousFrameIsSpare(index: Int) = rolls[index - 1] + rolls[index - 2] == 10

    private fun isFirstThrowOfNextFrame(index: Int) = index % 2 == 0

    private fun isNotFirstFrame(index: Int) = index > 1

    fun roll(pinsDown: Int) {
        rolls.add(pinsDown)
    }
}