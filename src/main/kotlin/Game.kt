const val TOTAL_PINS = 10
const val TOTAL_NUMBER_OF_FRAMES = 10

typealias PinsKnockedDown = Int
typealias Frames = MutableList<Frame>

class Game {

    private val frames: Frames = mutableListOf()

    fun roll(pinsKnockedDown: PinsKnockedDown) {
        when {
            frames.isEmpty() -> frames.add(Frame(pinsKnockedDown))
            else -> {
                val last = frames.last()
                when {
                    last.isComplete() -> frames.add(Frame(pinsKnockedDown))
                    else -> {
                        frames.remove(last)
                        frames.add(last + pinsKnockedDown)
                    }
                }
            }
        }
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
                    frame.isStrike() ->
                        TOTAL_PINS + this.after(index + 1).pinsKnockedDownOnFirstThrow()
                    else ->
                        frame.pinsKnockedDown
                }
            }.orElse { 0 }

    private fun Frames.after(frameNumber: Int): Maybe<Frame> = this.at(frameNumber + 1)

    private fun Maybe<Frame>.pinsKnockedDownOnFirstThrow(): PinsKnockedDown =
        this.map { it.pinsKnockedDownOnFirstThrow }.orElse { 0 }

}