import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class FrameIllegalRepresentations {

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun `false spare`() {
        expectedException.expect(IllegalArgumentException::class.java)
        Spare(1, 2)
    }

    @Test
    fun `false simple`() {
        expectedException.expect(IllegalArgumentException::class.java)
        Simple(2, 8)
    }

}