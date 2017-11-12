package com.eureka.katas.bowling

class Game {

    var listOfPinsDown = mutableListOf<Int>()

    fun score(): Int {
        return (0 until listOfPinsDown.size).map { currentRoll ->
            defaultScore(currentRoll) +
                    extraPointsForStrike(currentRoll) +
                    extraPointsForSpare(currentRoll)
        }.sum()
    }

    private fun extraPointsForSpare(currentRoll: Int) =
            if (isThereASparePending(currentRoll))
                listOfPinsDown[currentRoll]
            else 0


    private fun extraPointsForStrike(currentRoll: Int) =
            listOfPinsDown[currentRoll] * numberOfStrikePending(currentRoll)

    private fun defaultScore(roll: Int) = listOfPinsDown[roll]

    private fun numberOfStrikePending(roll: Int): Int {
        return (Math.max(0, roll - 2) until roll)
                .filter(this::isStrike)
                .count()
    }

    private fun isStrike(roll: Int) = listOfPinsDown[roll] == 10

    private fun isThereASparePending(roll: Int) =
            isFrameFirstThrow(roll) && previousFrameIsSpare(roll) && isNotABonusThrow(roll)

    private fun isNotABonusThrow(roll: Int): Boolean {
        return roll <= 20
    }


    private fun previousFrameIsSpare(roll: Int) =
            pinsDownFor(roll - 1) + pinsDownFor(roll - 2) == 10

    private fun pinsDownFor(roll: Int) = if (roll > 0 ) listOfPinsDown[roll] else 0

    private fun isFrameFirstThrow(roll: Int) = roll % 2 == 0

    fun roll(pinsDown: Int) {
        listOfPinsDown.add(pinsDown)
    }
}