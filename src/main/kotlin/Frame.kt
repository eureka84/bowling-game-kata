data class Frame(
    private val firstThrow: PinsKnockedDown? = null,
    private val secondThrow: PinsKnockedDown? = null
) {
    val pinsKnockedDownOnFirstThrow: PinsKnockedDown get() = firstThrow.orZero()
    val pinsKnockedDown: PinsKnockedDown get() = firstThrow.orZero() + secondThrow.orZero()

    operator fun plus(pinsKnockedDown: PinsKnockedDown): Frame =
        if (firstThrow == null) {
            this.copy(firstThrow = pinsKnockedDown)
        } else {
            this.copy(secondThrow = pinsKnockedDown)
        }

    fun isComplete(): Boolean = isStrike() || (firstThrow != null && secondThrow != null)

    fun isStrike(): Boolean = firstThrow == TOTAL_PINS

    fun isSpare(): Boolean = pinsKnockedDown == TOTAL_PINS

    private fun PinsKnockedDown?.orZero() = this.or(0)
    private fun PinsKnockedDown?.or(default: PinsKnockedDown) = this?:default
}
