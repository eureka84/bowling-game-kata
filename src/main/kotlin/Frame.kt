data class Frame(
    private val firstThrow: PinsKnockedDown? = null,
    private val secondThrow: PinsKnockedDown? = null
) {
    val pinsKnockedDownOnFirstThrow: Int = firstThrow.or(0) 
    val pinsKnockedDown: PinsKnockedDown = firstThrow.or(0) + secondThrow.or(0)

    operator fun plus(pinsKnockedDown: PinsKnockedDown): Frame =
        if (firstThrow == null) {
            this.copy(firstThrow = pinsKnockedDown)
        } else {
            this.copy(secondThrow = pinsKnockedDown)
        }

    fun isComplete(): Boolean = firstThrow == TOTAL_PINS || (firstThrow != null && secondThrow != null)

    fun isStrike(): Boolean = firstThrow == TOTAL_PINS

    fun isSpare(): Boolean = pinsKnockedDown == TOTAL_PINS

}
