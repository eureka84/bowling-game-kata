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
    }
}

