package com.eureka.katas.bowling

typealias PinsKnockedDown = Int
sealed class KFrame {
    abstract val firstThrow: PinsKnockedDown
    internal abstract fun add(k: PinsKnockedDown): KFrame
    companion object {
        fun create(n: PinsKnockedDown): KFrame = when (n){
            10 -> Strike
            else -> NonStrike(firstThrow = n)
        }
    }
}
object Strike: KFrame() {
    override fun add(k: PinsKnockedDown) = this
    override val firstThrow: PinsKnockedDown = TOTAL_PINS
}
class NonStrike(override val firstThrow: PinsKnockedDown, private val secondThrow: PinsKnockedDown = 0): KFrame() {
    override fun add(k: PinsKnockedDown) = NonStrike(firstThrow = this.firstThrow, secondThrow = k)
}

fun List<PinsKnockedDown>.toFrames(): List<KFrame> {

    fun loop(rs: List<PinsKnockedDown>, f: KFrame?, fs: List<KFrame>, throws: Int, k: PinsKnockedDown): List<KFrame> =
        if (rs.isEmpty()){
            if (f == null) fs else fs.plus(f)
        } else {
            val frame = if (f == null ) KFrame.create(k) else {f.add(k)}
            when (frame) {
                Strike -> loop(rs, null, fs.plus(frame), 0, 0)
                NonStrike(firstThrow = k) -> loop(rs.tail(), frame, fs, throws + 1, rs.head())
                else -> loop(rs, null, fs.plus(frame), 0, 0)
            }
        }

    return loop(this, null, listOf(), 0, 0)
}

typealias Frame = List<PinsKnockedDown>

fun Frame.isStrike(): Boolean = this.sum() == TOTAL_PINS && this.size == 1
fun Frame.isSpare(): Boolean = this.sum() == TOTAL_PINS && this.size == MAX_THROWS_PER_FRAME
fun Frame.firstThrow(): PinsKnockedDown = this.head()
fun Frame.pinsKnockedDown(): PinsKnockedDown = this.sum()
fun emptyFrame(): Frame = listOf()