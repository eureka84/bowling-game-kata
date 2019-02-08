const val TOTAL_PINS = 10
const val MAX_THROWS_PER_FRAME = 2
const val NUMBER_OF_FRAMES = 10

class Game {

    private val rolls = mutableListOf<PinsKnockedDown>()

    fun roll(p: PinsKnockedDown) {
        rolls.add(p)
    }

    fun score(): Int =
            rolls.toFrames().let { frames ->
                frames.mapIndexed { frameNumber, frame ->
                    when {
                        isBonusFrame(frameNumber) -> 0
                        frame is Strike ->
                            TOTAL_PINS + nextTwoThrowsPinsKnockedDown(frames, frameNumber)
                        frame is Spare ->
                            TOTAL_PINS + frames.at(frameNumber + 1).fold({ 0 }, { it.firstThrow })
                        else ->
                            frame.pinsKnockedDown
                    }
                }.sum()
            }

    private fun nextTwoThrowsPinsKnockedDown(frames: List<Frame>, frameNumber: Int) =
            frames.at(frameNumber + 1).fold(
                    { 0 },
                    { frame ->
                        when (frame) {
                            is Strike -> TOTAL_PINS + frames.at(frameNumber + 2).fold({ 0 }, { it.firstThrow })
                            else -> frame.pinsKnockedDown
                        }
                    })

    private fun isBonusFrame(frameNumber: Int) = frameNumber > NUMBER_OF_FRAMES - 1

}