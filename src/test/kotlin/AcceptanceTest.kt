import org.junit.Test
import kotlin.test.assertEquals

class AcceptanceTest {

    @Test
    fun noStrikesNorSpares() {
        val game = Game()

        (1..20).forEach { _ -> game.roll(4) }

        assert(game.score() == 80)
    }

    @Test
    fun allSpares() {
        val game = Game()

        (1..21).forEach { _ -> game.roll(5) }

        assertEquals(150 , game.score())
    }

    @Test
    fun perfectGame() {
        val game = Game()

        (1..12).forEach { _ -> game.roll(10) }

        assertEquals(300 , game.score())
    }

    @Test
    fun mixSparesAndStrikes() {
        val game = Game()

        (1..5).forEach { _ ->
            game.roll(4)
            game.roll(6)
            game.roll(10)
        }

        game.roll(10)
        game.roll(10)

        assertEquals(210 , game.score())
    }
}