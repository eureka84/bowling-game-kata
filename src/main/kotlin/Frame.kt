data class Frame(
    private val firstThrow: PinsKnockedDown? = null,
    private val secondThrow: PinsKnockedDown? = null
) {
    val pinsKnockedDownOnFirstThrow: PinsKnockedDown get() = firstThrow?:0
    val pinsKnockedDown: PinsKnockedDown get() = (firstThrow?:0) + (secondThrow?:0)

    operator fun plus(pinsKnockedDown: PinsKnockedDown): Frame =
        if (firstThrow == null) {
            this.copy(firstThrow = pinsKnockedDown)
        } else {
            this.copy(secondThrow = pinsKnockedDown)
        }

    fun isComplete(): Boolean = isStrike() || (firstThrow != null && secondThrow != null)

    fun isStrike(): Boolean = firstThrow == TOTAL_PINS

    fun isSpare(): Boolean = pinsKnockedDown == TOTAL_PINS

}
