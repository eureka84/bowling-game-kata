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
                            TOTAL_PINS + frames.after(frameNumber).firstThrow()
                        else ->
                            frame.pinsKnockedDown
                    }
                }.sum()
            }

    private fun isBonusFrame(frameNumber: Int) = frameNumber > NUMBER_OF_FRAMES - 1

    private fun nextTwoThrowsPinsKnockedDown(frames: Frames, frameNumber: Int) =
            frames.after(frameNumber).fold(
                    { 0 },
                    { frame ->
                        when (frame) {
                            is Strike -> TOTAL_PINS + frames.after(frameNumber + 1).firstThrow()
                            else -> frame.pinsKnockedDown
                        }
                    })

    private fun Frames.after(frameNumber: Int): Maybe<Frame> = this.at(frameNumber + 1)

    private fun Maybe<Frame>.firstThrow(): PinsKnockedDown = this.fold({ 0 }, { f -> f.firstThrow })

}