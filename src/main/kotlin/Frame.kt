typealias PinsKnockedDown = Int

sealed class Frame {
    abstract val firstThrow: PinsKnockedDown
    abstract val pinsKnockedDown: PinsKnockedDown
}

object Strike : Frame() {
    override val firstThrow: PinsKnockedDown = TOTAL_PINS
    override val pinsKnockedDown: PinsKnockedDown = TOTAL_PINS
}

data class Spare(
        override val firstThrow: PinsKnockedDown,
        private val secondThrow: PinsKnockedDown = 0
) : Frame() {
    override val pinsKnockedDown: PinsKnockedDown = TOTAL_PINS
}

data class Simple(
        override val firstThrow: PinsKnockedDown,
        private val secondThrow: PinsKnockedDown = 0
) : Frame() {
    override val pinsKnockedDown: PinsKnockedDown = firstThrow + secondThrow
}

fun List<PinsKnockedDown>.toFrames(): List<Frame> {
    val isFrameCompleted = { throws: Int, k: PinsKnockedDown ->
        throws == MAX_THROWS_PER_FRAME || k == TOTAL_PINS
    }

    fun loop(
            rs: List<PinsKnockedDown>,
            f: PseudoFrame,
            fs: List<PseudoFrame>,
            throws: Int,
            k: PinsKnockedDown
    ): List<PseudoFrame> =
            when {
                rs.isEmpty() ->
                    if (f.isEmpty()) fs else fs.plus<PseudoFrame>(f)
                isFrameCompleted(throws, k) ->
                    loop(rs, emptyFrame(), fs.plus<PseudoFrame>(f), 0, 0)
                else ->
                    loop(rs.tail(), f.plus(rs.head()), fs, throws + 1, k + rs.head())
            }

    return loop(this, emptyFrame(), listOf(), 0, 0).map { pseudoFrame ->
        when {
            pseudoFrame.size == 1 && pseudoFrame.sum() == TOTAL_PINS -> Strike
            pseudoFrame.size == 1 -> Simple(pseudoFrame[0]) // used for running calculations
            pseudoFrame.sum() == TOTAL_PINS -> Spare(pseudoFrame[0], pseudoFrame[1])
            else -> Simple(pseudoFrame[0], pseudoFrame[1])
        }
    }
}

typealias PseudoFrame = List<PinsKnockedDown>

fun emptyFrame(): PseudoFrame = listOf()