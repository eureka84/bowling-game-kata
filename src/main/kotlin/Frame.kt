typealias PinsKnockedDown = Int

sealed class Frame {
    abstract val firstThrow: PinsKnockedDown
    abstract val pinsKnockedDown: PinsKnockedDown

    companion object {
        fun from(pseudoFrame: PseudoFrame): Frame = when {
            pseudoFrame.size == 1 && pseudoFrame.sum() == TOTAL_PINS -> Strike
            pseudoFrame.size == 1 -> Simple(pseudoFrame[0]) // used for incomplete games
            pseudoFrame.sum() == TOTAL_PINS -> Spare(pseudoFrame[0], pseudoFrame[1])
            else -> Simple(pseudoFrame[0], pseudoFrame[1])
        }
    }
}

object Strike : Frame() {
    override val firstThrow: PinsKnockedDown = TOTAL_PINS
    override val pinsKnockedDown: PinsKnockedDown = TOTAL_PINS
}

data class Spare(
    override val firstThrow: PinsKnockedDown,
    private val secondThrow: PinsKnockedDown
) : Frame() {
    init {
        require(firstThrow + secondThrow == TOTAL_PINS) {
            "Spare pins knocked down should equal $TOTAL_PINS"
        }
    }

    override val pinsKnockedDown: PinsKnockedDown = TOTAL_PINS
}

data class Simple(
    override val firstThrow: PinsKnockedDown,
    private val secondThrow: PinsKnockedDown = 0
) : Frame() {
    init {
        require(firstThrow + secondThrow < TOTAL_PINS) {
            "If pins knocked down equal $TOTAL_PINS with 2 throws than it's a Spare"
        }
    }

    override val pinsKnockedDown: PinsKnockedDown = firstThrow + secondThrow
}

typealias Frames = List<Frame>
typealias PseudoFrame = List<PinsKnockedDown>

fun List<PinsKnockedDown>.toFrames(): Frames {
    fun loop(
        rolls: List<PinsKnockedDown>,
        pseudoFrame: PseudoFrame,
        result: Frames
    ): Frames =
        if (pseudoFrame.isComplete()) {
            loop(rolls, emptyPseudoFrame(), result + Frame.from(pseudoFrame))
        } else {
            rolls.headOpt()
                .map { head -> loop(rolls.tail(), pseudoFrame + head, result) }
                .orElse { if (pseudoFrame.isEmpty()) result else result + Frame.from(pseudoFrame) }
        }

    return loop(this, emptyPseudoFrame(), mutableListOf())
}

private fun PseudoFrame.isComplete(): Boolean = this.sum() == TOTAL_PINS || this.size == MAX_THROWS_PER_FRAME

private fun emptyPseudoFrame(): PseudoFrame = mutableListOf()