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
            val pinsDown = 4
            game.roll(pinsDown)
            game.score() shouldBe pinsDown
        }
    }
}

