const val TOTAL_PINS = 10
const val MAX_THROWS_PER_FRAME = 2
const val NUMBER_OF_FRAMES = 10

class Game {

    private val rolls = mutableListOf<PinsKnockedDown>()

    fun roll(p: PinsKnockedDown) {
        rolls.add(p)
    }

    fun score(): Int {
        val frames = rolls.toFrames()
        return frames.mapIndexed { frameNumber, frame ->
            when {
                isBonusFrame(frameNumber) -> 0
                frame is Strike -> TOTAL_PINS + nextTwoThrowsPinsKnockedDown(frames, frameNumber)
                frame is Spare -> TOTAL_PINS + frames[frameNumber + 1].firstThrow
                else -> frame.pinsKnockedDown
            }
        }.sum()
    }


    private fun nextTwoThrowsPinsKnockedDown(frames: List<KFrame>, frameNumber: Int) =
            when(frames[frameNumber + 1]) {
                Strike -> TOTAL_PINS + frames[frameNumber + 2].firstThrow
                else -> frames[frameNumber + 1].pinsKnockedDown
            }

    private fun isBonusFrame(frameNumber: Int) = frameNumber > NUMBER_OF_FRAMES - 1

}
