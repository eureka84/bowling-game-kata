import io.kotest.matchers.shouldBe
import org.junit.Test

class BowlingGameTest {

    @Test
    fun `should be able to tell the score after no roll`() {
        val game = Game()

        game.score() shouldBe 0
    }

    @Test
    fun `should be able to tell the score after one roll`() {
        val game = Game()

        game.roll(4)

        game.score() shouldBe 4
    }

    @Test
    fun `should be able to tell the score after two rolls with less than 10 pins down`() {
        val game = Game()

        game.roll(3)
        game.roll(4)

        game.score() shouldBe 7
    }

    @Test
    fun `should be able to tell the score after one spare`() {
        val game = Game()

        rollOneSpare(game)
        game.roll(3)

        game.score() shouldBe 16
    }

    @Test
    fun `should be able to tell the score after two spares`() {
        val game = Game()

        rollOneSpare(game)

        game.score() shouldBe 10

        game.roll(3)
        game.roll(4)
        rollOneSpare(game)
        game.roll(4)

        game.score() shouldBe 38
    }

    @Test
    fun `should be able to tell the score after two consecutive spares`() {
        val game = Game()

        rollOneSpare(game)
        rollOneSpare(game)
        game.roll(3)

        game.score() shouldBe 30
    }

    @Test
    fun `should be able to tell the score after one strike`() {
        val game = Game()

        rollAStrike(game)

        game.score() shouldBe 10

        game.roll(3)
        game.roll(4)

        game.score() shouldBe 24
    }

    @Test
    fun `should be able to tell the score after two non consecutive strikes`() {
        val game = Game()

        rollAStrike(game) // -> 17
        game.roll(3)
        game.roll(4)
        rollAStrike(game) // -> 15
        game.roll(4)
        game.roll(1)

        game.score() shouldBe 44
    }

    @Test
    fun `should be able to tell the score after two consecutive strikes`() {
        val game = Game()

        rollAStrike(game) // -> 24
        rollAStrike(game) // -> 15
        game.roll(4)
        game.roll(1)

        game.score() shouldBe 44
    }

    @Test
    fun `should be able to tell the score after three consecutive strikes`() {
        val game = Game()

        rollAStrike(game) // -> 30
        rollAStrike(game) // -> 24
        rollAStrike(game) // -> 15
        game.roll(4)
        game.roll(1)

        game.score() shouldBe 74

    }

    private fun rollAStrike(game: Game) = game.roll(10)

    private fun rollOneSpare(game: Game) {
        game.roll(4)
        game.roll(6)
    }
}

