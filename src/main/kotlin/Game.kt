package com.eureka.katas.bowling

const val TOTAL_PINS = 10
const val MAX_THROWS_PER_FRAME = 2
const val NUMBER_OF_FRAMES = 10

class Game {

    private val rolls = mutableListOf<PinsKnockedDown>()

    fun roll(p: PinsKnockedDown) {
        rolls.add(p)
    }

    fun score(): Int {
        val frames = computeFrames()
        return frames.mapIndexed { frameNumber, frame ->
            when {
                isBonusFrame(frameNumber) -> 0
                frame.isSpare() -> TOTAL_PINS + frames[frameNumber + 1].firstThrow()
                frame.isStrike() -> TOTAL_PINS + nextTwoThrowsPinsKnockedDown(frames, frameNumber)
                else -> frame.pinsKnockedDown()
            }
        }.sum()
    }

    private fun computeFrames(): List<Frame> {
        val isFrameCompleted = { throws: Int, k: PinsKnockedDown ->
            throws == MAX_THROWS_PER_FRAME || k == TOTAL_PINS
        }

        fun loop(rs: List<PinsKnockedDown>, f: Frame, fs: List<Frame>, throws: Int, k: PinsKnockedDown): List<Frame> =
                when {
                    rs.isEmpty() ->
                        if (f.isEmpty()) fs else fs.plus<Frame>(f)
                    isFrameCompleted(throws, k) ->
                        loop(rs, emptyFrame(), fs.plus<Frame>(f), 0, 0)
                    else ->
                        loop(rs.tail(), f.plus(rs.head()), fs, throws + 1, k + rs.head())
                }

        return loop(rolls, emptyFrame(), listOf(), 0, 0)
    }

    private fun nextTwoThrowsPinsKnockedDown(frames: List<Frame>, frameNumber: Int) =
            when {
                frames[frameNumber + 1].isStrike() -> TOTAL_PINS + frames[frameNumber + 2].firstThrow()
                else -> frames[frameNumber + 1].pinsKnockedDown()
            }

    private fun isBonusFrame(frameNumber: Int) = frameNumber > NUMBER_OF_FRAMES - 1

}
