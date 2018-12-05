package com.eureka.katas.bowling

typealias Frame = List<Int>

fun Frame.isStrike(): Boolean = this.sum() == 10 && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == 10 && this.size == 2

class Game {

    private val rolls = mutableListOf<Int>()

    fun roll(pinsKnockedDown: Int) {
        rolls.add(pinsKnockedDown)
    }

    fun score(): Int {
        val frames = computeFrames()
        val nextTwoThrowsPinsKnockedDown = { i: Int ->
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
                frame.isStrike() -> 10 + nextTwoThrowsPinsKnockedDown(index)
                else -> frame.sum()
            }
        }.sum()
    }

    private fun computeFrames(): List<Frame> {
        fun loop(rolls: List<Int>, frame: Frame, frames: List<Frame>, throws: Int, knockedDown: Int): List<Frame> {
            val isFrameCompleted = throws == 2 || knockedDown == 10
            return when {
                rolls.isEmpty() ->
                    if (frame.isEmpty()) frames else frames.plus<Frame>(frame)
                isFrameCompleted ->
                    loop(rolls, listOf(), frames.plus<Frame>(frame), 0, 0)
                else ->
                    loop(rolls.subList(1, rolls.size), frame.plus(rolls[0]), frames, throws + 1, knockedDown + rolls[0])
            }
        }
        return loop(rolls, listOf(), listOf(), 0, 0)
    }

}