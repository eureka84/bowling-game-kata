package com.eureka.katas.bowling

const val TOTAL_PINS = 10
const val MAX_THROWS_PER_FRAME = 2
const val NUMBER_OF_FRAMES = 10

typealias PinsKnockedDown = Int
typealias Frame = List<Int>

class Game {

    private val rolls = mutableListOf<PinsKnockedDown>()

    fun roll(p: PinsKnockedDown) {
        rolls.add(p)
    }

    fun score(): Int {
        val frames = computeFrames()
        val nextTwoThrowsPinsKnockedDown = { i: Int ->
            when {
                frames[i + 1].isStrike() -> TOTAL_PINS + frames[i + 2][0]
                else -> frames[i + 1].sum()
            }
        }

        return frames.mapIndexed { index, frame ->
            val isBonusFrame = index > NUMBER_OF_FRAMES - 1
            when {
                isBonusFrame -> 0
                frame.isSpare() -> TOTAL_PINS + frames[index + 1][0]
                frame.isStrike() -> TOTAL_PINS + nextTwoThrowsPinsKnockedDown(index)
                else -> frame.sum()
            }
        }.sum()
    }

    private fun computeFrames(): List<Frame> {
        fun loop(rolls: List<PinsKnockedDown>, frame: Frame, frames: List<Frame>, throws: Int, k: PinsKnockedDown): List<Frame> {
            val isFrameCompleted = throws == MAX_THROWS_PER_FRAME || k == TOTAL_PINS
            return when {
                rolls.isEmpty() ->
                    if (frame.isEmpty()) frames else frames.plus<Frame>(frame)
                isFrameCompleted ->
                    loop(rolls, listOf(), frames.plus<Frame>(frame), 0, 0)
                else ->
                    loop(rolls.tail(), frame.plus(rolls.head()), frames, throws + 1, k + rolls.head())
            }
        }
        return loop(rolls, listOf(), listOf(), 0, 0)
    }

}

fun Frame.isStrike(): Boolean = this.sum() == TOTAL_PINS && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == TOTAL_PINS && this.size == MAX_THROWS_PER_FRAME

private fun <T> List<T>.tail(): List<T> =
        if (this.isEmpty()) throw IllegalAccessException("Tail of empty list") else this.subList(1, this.size)

private fun <T> List<T>.head(): T =
        if (this.isEmpty()) throw IllegalAccessException("Head of empty list") else this[0]