package com.eureka.katas.bowling

class Game {

    private val listOfPinsDown = mutableListOf<Int>()
    private val frames: Map<Int, Int>
        get() {
            var total = 0
            var throws = 0
            var frame = 1
            val currentFrames = mutableMapOf<Int, Int>()
            listOfPinsDown.forEachIndexed { index, pins ->
                total += pins
                throws++
                currentFrames.put(index + 1, frame)
                if (total == 10 || throws == 2) {
                    frame++
                    throws = 0
                    total = 0
                }
            }
            return currentFrames
        }


    fun score(): Int {
        return basePoints()
                .add(pointsExtraForSpare())
                .add(pointsExtraForStrike())
                .sum()
    }

    private fun List<Int>.add(other: List<Int>): List<Int> {
        return this.zip(other).map { (a, b) -> a + b }
    }

    private fun basePoints(): List<Int> {
        return (1..listOfPinsDown.size).map { currentRoll ->
            if (isNotBonusThrow(currentRoll))
                listOfPinsDown[currentRoll - 1]
            else 0
        }
    }

    private fun isNotBonusThrow(roll: Int) = frames[roll]!! <= 10

    private fun pointsExtraForSpare(): List<Int> {
        return (1..listOfPinsDown.size).map { currentRoll ->
            if (isThereASparePending(currentRoll))
                listOfPinsDown[currentRoll - 1]
            else 0
        }
    }

    private fun isThereASparePending(roll: Int) =
            isFramesFirstThrow(roll) && previousFrameIsSpare(roll) && frames[roll]!! <= 11

    // ISSUE HERE
    private fun isFramesFirstThrow(roll: Int) = roll % 2 == 1

    /// ISSUE HERE
    private fun previousFrameIsSpare(roll: Int) =
            pinsDownFor(roll - 1) + pinsDownFor(roll - 2) == 10

    private fun pinsDownFor(roll: Int) = if (roll >= 1) listOfPinsDown[roll - 1] else 0

    private fun pointsExtraForStrike(): List<Int> {
        return (1..listOfPinsDown.size).map { currentRoll ->
            listOfPinsDown[currentRoll - 1] * numberOfStrikesPending(currentRoll)
        }
    }

    private fun numberOfStrikesPending(roll: Int) =
            (Math.max(1, roll - 2) until roll)
                    .filter{ isNotBonusThrow(it) && listOfPinsDown[it - 1] == 10}
                    .count()

    fun roll(pinsDown: Int) {
        listOfPinsDown.add(pinsDown)
    }

}