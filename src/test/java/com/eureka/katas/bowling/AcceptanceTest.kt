package com.eureka.katas.bowling

import org.junit.Test

/**
 * @author asciarra
 */
class AcceptanceTest {

    @Test
    fun noStrikesNorSpares() {
        val game = Game()

        (1..20).forEach { game.roll(4) }

        assert(game.score() == 80)
    }
}