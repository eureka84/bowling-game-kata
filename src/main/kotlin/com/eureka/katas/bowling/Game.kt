package com.eureka.katas.bowling

class Game {

    var listOfPinsDown = mutableListOf<Int>()

    fun score(): Int {
        return (1..listOfPinsDown.size).map { currentRoll ->
            defaultScore(currentRoll) +
                    extraPointsForStrike(currentRoll) +
                    extraPointsForSpare(currentRoll)
        }.sum()
    }

    private fun defaultScore(roll: Int) = listOfPinsDown[indexOf(roll)]

    private fun extraPointsForStrike(roll: Int) =
            listOfPinsDown[indexOf(roll)] * numberOfStrikePending(roll)

    private fun numberOfStrikePending(roll: Int) =
            (Math.max(1, roll - 2) until roll)
                    .filter(this::isStrike)
                    .count()

    private fun isStrike(roll: Int) =
            isNotABonusThrow(roll) && listOfPinsDown[indexOf(roll)] == 10

    private fun extraPointsForSpare(roll: Int) =
            if (isThereASparePending(roll) && isNotABonusThrow(roll))
                listOfPinsDown[indexOf(roll)]
            else 0

    private fun isThereASparePending(roll: Int) =
            isFramesFirstThrow(roll) && previousFrameIsSpare(roll)

    private fun isNotABonusThrow(roll: Int): Boolean {
        return roll <= 20
    }

    private fun previousFrameIsSpare(roll: Int) =
            pinsDownFor(roll - 1) + pinsDownFor(roll - 2) == 10

    private fun pinsDownFor(roll: Int) = if (roll >= 1) listOfPinsDown[indexOf(roll)] else 0

    private fun isFramesFirstThrow(roll: Int) = roll % 2 == 1

    private fun indexOf(roll: Int) = roll - 1

    fun roll(pinsDown: Int) {
        listOfPinsDown.add(pinsDown)
    }

}