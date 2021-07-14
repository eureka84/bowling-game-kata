const val TOTAL_PINS = 10
const val TOTAL_NUMBER_OF_FRAMES = 10

typealias PinsKnockedDown = Int

class Game {

    private var context: Context = Context()
    private val frames: Frames get() = context.frames

    fun roll(p: PinsKnockedDown) {
        context += p
    }

    fun score(): Int =
        framesUpToBonusFrame()
            .mapIndexed { index, frame ->
                when {
                    frame.isStrike() -> TOTAL_PINS + frames.pinsKnockedDownOnNextTwoThrowsAfter(index)
                    frame.isSpare() -> TOTAL_PINS + frames.after(index).pinsKnockedDownOnFirstThrow()
                    else -> frame.pinsKnockedDown
                }
            }.sum()

    private fun framesUpToBonusFrame() = frames.take(TOTAL_NUMBER_OF_FRAMES)

    private fun Frames.pinsKnockedDownOnNextTwoThrowsAfter(index: Int) =
        this
            .after(index)
            .map { frame ->
                when {
                    frame.isStrike() -> TOTAL_PINS + this.after(index + 1).pinsKnockedDownOnFirstThrow()
                    else -> frame.pinsKnockedDown
                }
            }.orElse { 0 }

    private fun Frames.after(frameNumber: Int): Maybe<Frame> = this.at(frameNumber + 1)

    private fun Maybe<Frame>.pinsKnockedDownOnFirstThrow(): PinsKnockedDown =
        this.map { it.pinsKnockedDownOnFirstThrow }.orElse { 0 }

    class Context(private val rolls: List<PinsKnockedDown> = listOf()) {
        val frames: Frames by lazy { rolls.toFrames() }

        operator fun plus(pins: PinsKnockedDown): Context {
            return Context(rolls + pins)
        }
    }

}