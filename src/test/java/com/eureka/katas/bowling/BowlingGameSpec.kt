package com.eureka.katas.bowling

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

/*
 * @author asciarra
 */
class BowlingGameSpec: StringSpec() {
    init {

        "should be able to tell the score after no roll" {
            val game = Game()
            game.score() shouldBe 0
        }

        "should be able to tell the score after one roll" {
            val game = Game()
            game.roll(4)
            game.score() shouldBe 4
        }

        "should be able to tell the score after two rolls with less than 10 pins down" {
            val game = Game()
            game.roll(3)
            game.roll(4)
            game.score() shouldBe 7
        }


//        "should be able to tell the score after one spare" {
//            val game = Game()
//            rollOneSpare(game)
//            game.roll(3)
//            game.score() shouldBe 16
//        }

    }

    private fun rollOneSpare(game: Game) {
        game.roll(3)
        game.roll(7)
    }
}

