import Frame.Companion.emptyFrame

typealias PinsKnockedDown = Int

typealias Frames = List<Frame>

data class Frame(private val firstThrow: PinsKnockedDown? = null, private val secondThrow: PinsKnockedDown? = null) {

    val pinsKnockedDownOnFirstThrow: Int = firstThrow.or(0) 
    val pinsKnockedDown: PinsKnockedDown = firstThrow.or(0) + secondThrow.or(0)

    operator fun plus(pinsKnockedDown: PinsKnockedDown): Frame =
        if (firstThrow == null) {
            this.copy(firstThrow = pinsKnockedDown)
        } else {
            this.copy(secondThrow = pinsKnockedDown)
        }

    fun isEmpty(): Boolean {
        return firstThrow == null && secondThrow == null
    }

    fun isComplete(): Boolean = firstThrow == TOTAL_PINS || (firstThrow != null && secondThrow != null)

    fun isStrike(): Boolean = firstThrow == TOTAL_PINS

    fun isSpare(): Boolean = pinsKnockedDown == TOTAL_PINS

    companion object {
        fun emptyFrame() = Frame()
    }
}

fun List<PinsKnockedDown>.toFrames(): Frames {
    fun loop(
        rolls: List<PinsKnockedDown>,
        frame: Frame,
        result: Frames
    ): Frames =
        if (frame.isComplete()) {
            loop(rolls, emptyFrame(), result + frame)
        } else {
            rolls.headOpt()
                .map { head -> loop(rolls.tail(), frame + head, result) }
                .orElse { if (frame.isEmpty()) result else result + frame }
        }

    return loop(this, emptyFrame(), mutableListOf())
}