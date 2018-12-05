package com.eureka.katas.bowling

typealias Frame = List<Int>

fun Frame.isStrike(): Boolean = this.sum() == 10 && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == 10 && this.size == 2

class Game {

    private val listOfPinsDown = mutableListOf<Int>()

    private val frames: List<Frame>
        get() {
        fun loop(pins: List<Int>, curr: Frame, acc: List<Frame>, numberOfThrows: Int, sum: Int): List<Frame> {
            val isFrameCompleted = numberOfThrows == 2 || sum == 10
            return when {
                pins.isEmpty() -> if (curr.isEmpty()) acc else acc.plus<Frame>(curr)
                isFrameCompleted -> loop(pins, listOf(), acc.plus<Frame>(curr), 0, 0)
                else -> loop(pins.subList(1, pins.size), curr.plus(pins[0]), acc, numberOfThrows + 1, sum + pins[0])
            }
        }
        return loop(listOfPinsDown, listOf(), listOf(), 0, 0)
    }

    fun score(): Int {
        val nextTwoRolls = { i: Int ->
            when {
                frames[i + 1].isStrike() -> 10 + frames[i + 2][0]
                else -> frames[i + 1].sum()
            }
        }

        return frames.mapIndexed { index, frame ->
            val isBonusFrame = index > 9
            when {
                isBonusFrame -> 0
                frame.isSpare() -> 10 + frames[index + 1][0]
                frame.isStrike() -> 10 + nextTwoRolls(index)
                else -> frame.sum()
            }
        }.sum()
    }

    fun roll(pinsDown: Int) {
        listOfPinsDown.add(pinsDown)
    }

}