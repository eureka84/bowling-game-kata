package com.eureka.katas.bowling

class Game {

    private var listOfPinsDown = mutableListOf<Int>()
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
        val sum: (Int, Int) -> Int = { left, right -> left + right }
        return basePoints()
                .zip(extraForStrike(), sum)
                .zip(extraForSpare(), sum)
                .sum()

    }

    private fun extraForSpare(): List<Int> {
        return (1..listOfPinsDown.size).map { currentRoll ->
            if (isThereASparePending(currentRoll))
                listOfPinsDown[indexOf(currentRoll)]
            else 0
        }
    }

    private fun extraForStrike(): List<Int> {
        return (1..listOfPinsDown.size).map { currentRoll ->
            listOfPinsDown[indexOf(currentRoll)] * numberOfStrikesPending(currentRoll)
        }
    }

    private fun basePoints(): List<Int> {
        return (1..listOfPinsDown.size).map { currentRoll ->
            if (isNotBonusThrow(currentRoll))
                listOfPinsDown[indexOf(currentRoll)]
            else 0
        }
    }

    private fun numberOfStrikesPending(roll: Int) =
            (Math.max(1, roll - 2) until roll)
                    .filter(this::isStrike)
                    .count()

    private fun isStrike(roll: Int) =
            isNotBonusThrow(roll) && listOfPinsDown[indexOf(roll)] == 10

    private fun isNotBonusThrow(roll: Int) = frames[roll]!! <= 10

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