package com.eureka.katas.bowling

class Game {

    var pinsDownForRoll = mutableListOf<Int>()

    fun score(): Int {
        var total = 0
        pinsDownForRoll.forEachIndexed { index, pinsDown ->
            total += pinsDown
            if (isThereAStrikePending(index) || isThereASparePending(index)) {
                total += pinsDown
            }
        }
        return total
    }

    private fun isThereAStrikePending(roll: Int): Boolean {
        return  (roll > 0 && isStrike(roll - 1))  ||
                (roll > 1 && isStrike(roll - 2))
    }

    private fun isStrike(index: Int) = pinsDownForRoll[index] == 10

    private fun isThereASparePending(index: Int): Boolean {
        return index > 1 &&
                isFrameFirstThrow(index) &&
                previousFrameIsSpare(index)
    }

    private fun previousFrameIsSpare(index: Int) = pinsDownForRoll[index - 1] + pinsDownForRoll[index - 2] == 10

    private fun isFrameFirstThrow(index: Int) = index % 2 == 0

    fun roll(pinsDown: Int) {
        pinsDownForRoll.add(pinsDown)
    }
}