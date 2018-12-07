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
        val frames = currentFrames()
        return frames.mapIndexed { frameNumber, frame ->
            when {
                isBonusFrame(frameNumber) -> 0
                frame.isSpare() -> TOTAL_PINS + frames[frameNumber + 1].firstThrow()
                frame.isStrike() -> TOTAL_PINS + nextTwoThrowsPinsKnockedDown(frames, frameNumber)
                else -> frame.sum()
            }
        }.sum()
    }

    private fun currentFrames(): List<Frame> {
        val isFrameCompleted = { throws: Int, k: PinsKnockedDown -> throws == MAX_THROWS_PER_FRAME || k == TOTAL_PINS }
        fun loop(ps: List<PinsKnockedDown>, f: Frame, fs: List<Frame>, throws: Int, k: PinsKnockedDown): List<Frame> {
            return when {
                ps.isEmpty() -> if (f.isEmpty()) fs else fs.plus<Frame>(f)
                isFrameCompleted(throws, k) -> loop(ps, listOf(), fs.plus<Frame>(f), 0, 0)
                else -> loop(ps.tail(), f.plus(ps.head()), fs, throws + 1, k + ps.head())
            }
        }
        return loop(rolls, listOf(), listOf(), 0, 0)
    }

    private fun nextTwoThrowsPinsKnockedDown(frames: List<Frame>, frameNumber: Int) =
        when {
            frames[frameNumber + 1].isStrike() -> TOTAL_PINS + frames[frameNumber + 2].firstThrow()
            else -> frames[frameNumber + 1].sum()
        }

    private fun isBonusFrame(frameNumber: Int) = frameNumber > NUMBER_OF_FRAMES - 1

}

fun Frame.isStrike(): Boolean = this.sum() == TOTAL_PINS && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == TOTAL_PINS && this.size == MAX_THROWS_PER_FRAME
fun Frame.firstThrow(): PinsKnockedDown = this[0]

private fun <T> List<T>.tail(): List<T> =
        if (this.isEmpty()) throw IllegalAccessException("Tail of empty list") else this.subList(1, this.size)

private fun <T> List<T>.head(): T =
        if (this.isEmpty()) throw IllegalAccessException("Head of empty list") else this[0]