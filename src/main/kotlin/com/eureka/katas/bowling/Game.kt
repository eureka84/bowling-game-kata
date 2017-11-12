package com.eureka.katas.bowling

class Game {

    var pinsDownForRoll = mutableListOf<Int>()

    fun score(): Int {
        var total = 0
        pinsDownForRoll.forEachIndexed { currentRoll, pinsDown ->
            total += pinsDown
            if (isThereAStrikePendingBefore(currentRoll)){
                total += pinsDown * numberOfStrikePendingBefore(currentRoll)
            }
            if (isThereASparePendingBefore(currentRoll))
                total += pinsDown
        }
        return total
    }

    private fun numberOfStrikePendingBefore(roll: Int): Int {
        val previousTwoRolls = pinsDownForRoll.subList(Math.max(0, roll - 2), roll)
        return previousTwoRolls
                .filter { isStrike(it) }
                .count()
    }

    private fun isThereAStrikePendingBefore(roll: Int): Boolean {
        val previousRollPinsDown = if (roll > 0) pinsDownForRoll[roll - 1] else 0
        val secondToLastPinsDown = if (roll > 1) pinsDownForRoll[roll - 2] else 0

        return  isStrike(previousRollPinsDown)  ||
                isStrike(secondToLastPinsDown)
    }

    private fun isStrike(roll: Int) = roll == 10

    private fun isThereASparePendingBefore(roll: Int): Boolean {
        return roll > 1 &&
                isFrameFirstThrow(roll) &&
                previousFrameIsSpare(roll)
    }

    private fun previousFrameIsSpare(index: Int) = pinsDownForRoll[index - 1] + pinsDownForRoll[index - 2] == 10

    private fun isFrameFirstThrow(index: Int) = index % 2 == 0

    fun roll(pinsDown: Int) {
        pinsDownForRoll.add(pinsDown)
    }
}