package com.eureka.katas.bowling

class Game {

    var pinsDownForRoll = mutableListOf<Int>()

    fun score(): Int {
        var total = 0
        pinsDownForRoll.forEachIndexed { index, pinsDown ->
            total += pinsDown
            if (isThereAStrikePending(index)){
                total += pinsDown * numberOfStrikePending (index)
            }
            if (isThereASparePending(index))
                total += pinsDown
        }
        return total
    }

    private fun numberOfStrikePending(roll: Int): Int {
        return pinsDownForRoll
                .subList(Math.max(0, roll -2), roll)
                .filter { isStrike(it) }
                .count()
    }

    private fun isThereAStrikePending(roll: Int): Boolean {
        return  (roll > 0 && isStrike(pinsDownForRoll[roll - 1]))  ||
                (roll > 1 && isStrike(pinsDownForRoll[roll - 2]))
    }

    private fun isStrike(roll: Int) = roll == 10

    private fun isThereASparePending(roll: Int): Boolean {
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