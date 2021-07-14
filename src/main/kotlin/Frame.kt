import PseudoFrame.Companion.emptyPseudoFrame

typealias PinsKnockedDown = Int

sealed class Frame {
    abstract val firstThrow: PinsKnockedDown
    abstract val pinsKnockedDown: PinsKnockedDown

    companion object {
        fun from(pseudoFrame: PseudoFrame): Frame = when {
            pseudoFrame.isStrike() -> Strike
            pseudoFrame.isSpare() -> Spare(pseudoFrame.firstThrow!!, pseudoFrame.secondThrow!!)
            pseudoFrame.isPartial() -> Simple(pseudoFrame.firstThrow!!)
            else -> Simple(pseudoFrame.firstThrow!!, pseudoFrame.secondThrow!!)
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

data class PseudoFrame(val firstThrow: PinsKnockedDown? = null, val secondThrow: PinsKnockedDown? = null) {
    fun isEmpty(): Boolean {
        return firstThrow == null && secondThrow == null
    }

    operator fun plus(pinsKnockedDown: PinsKnockedDown): PseudoFrame =
        if (firstThrow == null) {
            PseudoFrame(pinsKnockedDown)
        } else {
            this.copy(secondThrow = pinsKnockedDown)
        }

    fun isComplete(): Boolean = firstThrow == TOTAL_PINS || (firstThrow != null && secondThrow != null)

    fun isStrike(): Boolean = firstThrow == TOTAL_PINS

    fun isSpare(): Boolean = (firstThrow.or(0) + secondThrow.or(0)) == TOTAL_PINS

    fun isPartial(): Boolean = firstThrow != null && secondThrow == null

    private fun Int?.or(default: Int): Int = this?:default

    companion object {
        fun emptyPseudoFrame() = PseudoFrame()
    }

}

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