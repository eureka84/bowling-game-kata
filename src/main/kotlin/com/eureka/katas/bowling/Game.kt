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
                currentFrames.put(index, frame)
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

    private fun List<Int>.add(other: List<Int>): List<Int> = this.zip(other).map { (a, b) -> a + b }

    private fun basePoints(): List<Int> {
        return (0 until listOfPinsDown.size).map { currentRoll ->
            if (isNotABonusThrow(currentRoll))
                listOfPinsDown[currentRoll]
            else
                0
        }
    }


    private fun pointsExtraForSpare(): List<Int> {
        return (0 until listOfPinsDown.size).map { currentRoll ->
            if (isThereASparePending(currentRoll))
                listOfPinsDown[currentRoll]
            else 0
        }
    }
    private fun isNotABonusThrow(roll: Int) = frames[roll]!! <= 10

    private fun isThereASparePending(roll: Int): Boolean =
            isFramesFirstThrow(roll) && previousFrameIsSpare(roll)

    private fun isFramesFirstThrow(roll: Int): Boolean {
        val currentRollFrame = frames[roll]!!
        val previousRollFrame = if (frames[roll - 1] == null) -1 else frames[roll - 1]!!
        return currentRollFrame > previousRollFrame
    }

    private fun previousFrameIsSpare(roll: Int): Boolean {
        val previousRoll = roll - 1
        val secondToLastRoll = roll - 2

        return (previousRoll >= 0 && secondToLastRoll >= 0) &&
            listOfPinsDown[previousRoll]  + listOfPinsDown[secondToLastRoll] == 10
    }

    private fun pointsExtraForStrike(): List<Int> {
        return (0 until listOfPinsDown.size).map { currentRoll ->
            listOfPinsDown[currentRoll] * numberOfStrikesPending(currentRoll)
        }
    }

    private fun numberOfStrikesPending(roll: Int) =
            (Math.max(0, roll - 2) until roll)
                    .filter{
                        isNotABonusThrow(it) && listOfPinsDown[it] == 10
                    }
                    .count()

    fun roll(pinsDown: Int) {
        listOfPinsDown.add(pinsDown)
    }

}