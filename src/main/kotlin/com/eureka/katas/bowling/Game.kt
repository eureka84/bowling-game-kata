package com.eureka.katas.bowling

class Game {

    private var listOfPinsDown = mutableListOf<Int>()
    private val frames: Map<Int, Int> get() {
        var total = 0
        var throws = 0
        var frame = 1
        val currentFrames = mutableMapOf<Int, Int>()
        listOfPinsDown.forEachIndexed { index, pins ->
            total += pins
            throws++
            currentFrames.put(index+1, frame)
            if (total == 10 || throws == 2) {
                frame++
                throws = 0
                total = 0
            }
        }
        return currentFrames
    }


    fun score(): Int {
        return (1..listOfPinsDown.size).map { currentRoll ->
            defaultScore(currentRoll) +
                    extraPointsForStrike(currentRoll) +
                    extraPointsForSpare(currentRoll)
        }.sum()
    }

    private fun defaultScore(roll: Int) =
            if (isNotBonusThrow(roll))  listOfPinsDown[indexOf(roll)] else 0

    private fun extraPointsForStrike(roll: Int) =
             listOfPinsDown[indexOf(roll)] * numberOfStrikePending(roll)

    private fun numberOfStrikePending(roll: Int) =
            (Math.max(1, roll - 2) until roll)
                    .filter(this::isStrike)
                    .count()

    private fun isStrike(roll: Int) =
            isNotBonusThrow(roll) && listOfPinsDown[indexOf(roll)] == 10

    private fun isNotBonusThrow(roll: Int) = frames[roll]!! <= 10

    private fun extraPointsForSpare(roll: Int) =
            if (isThereASparePending(roll))
                listOfPinsDown[indexOf(roll)]
            else 0

    private fun isThereASparePending(roll: Int) =
            isFramesFirstThrow(roll) && previousFrameIsSpare(roll) && frames[roll]!! <= 11

    private fun previousFrameIsSpare(roll: Int) =
            pinsDownFor(roll - 1) + pinsDownFor(roll - 2) == 10

    private fun pinsDownFor(roll: Int) = if (roll >= 1) listOfPinsDown[indexOf(roll)] else 0

    private fun isFramesFirstThrow(roll: Int) = roll % 2 == 1

    private fun indexOf(roll: Int) = roll - 1

    fun roll(pinsDown: Int) {
        listOfPinsDown.add(pinsDown)
    }

}