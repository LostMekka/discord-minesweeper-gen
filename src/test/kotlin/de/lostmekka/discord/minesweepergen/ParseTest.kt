package de.lostmekka.discord.minesweepergen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ParseTest : StringSpec({
    "parse printed grid" {
        repeat(50) {
            val grid1 = Grid.generate(10, 10, 15)
            val debugString = grid1.debugString(revealed = true)
            val grid2 = Grid.parse(debugString)
            val n1 = grid1.map { it.number }
            val n2 = grid2.map { it.number }
            n1 shouldBe n2
        }
    }
})
