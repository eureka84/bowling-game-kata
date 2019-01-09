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
        private val secondThrow: PinsKnockedDown
) : Frame() {
    init {
        require(firstThrow + secondThrow == TOTAL_PINS)
        { "Spare pinsKnockerDown should equal $TOTAL_PINS" }
    }

    override val pinsKnockedDown: PinsKnockedDown = TOTAL_PINS
}

data class Simple(
        override val firstThrow: PinsKnockedDown,
        private val secondThrow: PinsKnockedDown = 0
) : Frame() {
    init {
        require(firstThrow + secondThrow < TOTAL_PINS)
        { "If pinsKnockerDown equal $TOTAL_PINS with 2 throws than it's a Spare" }
    }

    override val pinsKnockedDown: PinsKnockedDown = firstThrow + secondThrow
}

fun List<PinsKnockedDown>.toFrames(): List<Frame> {
    val isFrameCompleted = { throws: Int, k: PinsKnockedDown ->
        throws == MAX_THROWS_PER_FRAME || k == TOTAL_PINS
    }

    fun loop(rs: List<PinsKnockedDown>, f: PseudoFrame, fs: List<Frame>, throws: Int, k: PinsKnockedDown)
            : List<Frame> =
            when {
                rs.isEmpty() ->
                    if (f.isEmpty()) fs else fs.plus(f.toFrame())
                isFrameCompleted(throws, k) ->
                    loop(rs, emptyPseudoFrame(), fs.plus(f.toFrame()), 0, 0)
                else ->
                    loop(rs.tail(), f.plus(rs.head()), fs, throws + 1, k + rs.head())
            }

    return loop(this, emptyPseudoFrame(), listOf(), 0, 0)
}

typealias PseudoFrame = List<PinsKnockedDown>
fun PseudoFrame.toFrame(): Frame =
        when {
            this.size == 1 && this.sum() == TOTAL_PINS -> Strike
            this.size == 1 -> Simple(this[0]) // used for incomplete games
            this.sum() == TOTAL_PINS -> Spare(this[0], this[1])
            else -> Simple(this[0], this[1])
        }

fun emptyPseudoFrame(): PseudoFrame = listOf()