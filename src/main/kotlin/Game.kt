const val TOTAL_PINS = 10
const val TOTAL_NUMBER_OF_FRAMES = 10

class Game {

    private var context: Context = Context()
    private val frames: Frames get() = context.frames

    fun roll(p: PinsKnockedDown) {
        context += p
    }

    fun score(): Int =
        frames
            .take(TOTAL_NUMBER_OF_FRAMES)
            .mapIndexed { currentFrameIndex, frame ->
                when {
                    frame.isStrike() -> TOTAL_PINS + pinsKnockedDownOnNextTwoThrows(frames, currentFrameIndex)
                    frame.isSpare() -> TOTAL_PINS + frames.after(currentFrameIndex).firstThrow()
                    else -> frame.pinsKnockedDown
                }
            }.sum()

    private fun pinsKnockedDownOnNextTwoThrows(frames: Frames, frameNumber: Int) =
        frames
            .after(frameNumber)
            .map { frame ->
                when {
                    frame.isStrike() -> TOTAL_PINS + frames.after(frameNumber + 1).firstThrow()
                    else -> frame.pinsKnockedDown
                }
            }.orElse { 0 }

    private fun Frames.after(frameNumber: Int): Maybe<Frame> = this.at(frameNumber + 1)

    private fun Maybe<Frame>.firstThrow(): PinsKnockedDown = this.map { it.firstThrow.or(0) }.orElse { 0 }

    class Context(private val rolls: List<PinsKnockedDown> = listOf()) {
        val frames: Frames by lazy { rolls.toFrames() }

        operator fun plus(pins: PinsKnockedDown): Context {
            return Context(rolls + pins)
        }
    }

}