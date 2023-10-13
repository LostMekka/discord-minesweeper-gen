package de.lostmekka.discord.minesweepergen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

private const val debugPrintColumns = 5

class SolverTest : StringSpec({
    "simple implicit reveals" {
        val grid = Grid.parse(
            """
            _ _ _
            1 1 1
            1 * 1
            """.trimIndent()
        )
        grid.autoRevealStartingArea(0, 0)
        solve(grid, debugPrintColumns) shouldBe true
    }
    "implicit bomb" {
        val grid = Grid.parse(
            """
            _ _ _
            1 2 1
            * 2 *
            """.trimIndent()
        )
        grid.autoRevealStartingArea(0, 0)
        solve(grid, debugPrintColumns) shouldBe true
    }
    "hardcore implicit reveals" {
        val grid = Grid.parse(
            """
            2 * 2 2 * 2 2 1 1 _
            * 2 2 * 3 * 2 * 1 _
            1 2 2 2 2 1 2 1 1 _
            _ 1 * 3 2 1 _ _ _ _
            _ 1 2 * * 2 _ _ _ _
            1 1 2 4 * 3 _ 1 1 1
            1 * 1 3 * 3 _ 1 * 1
            1 1 1 2 * 2 _ 1 1 1
            _ _ _ 1 1 1 _ _ 1 1
            _ _ _ _ _ _ _ _ 1 *
            """.trimIndent()
        )
        grid.autoRevealStartingArea(0, 3)
        solve(grid, debugPrintColumns) shouldBe true
    }
    "implicit reveals using non-adjacent tiles" {
        val grid = Grid.parse(
            """
            _ _ _ 1 1 1 _ _ 1 *
            _ _ _ 1 * 1 _ _ 1 1
            _ _ _ 2 2 2 1 2 2 1
            _ _ _ 1 * 1 1 * * 1
            1 1 1 2 2 2 2 3 2 1
            * 4 3 * 2 2 * 1 1 1
            * * * 3 * 2 1 1 2 *
            2 3 2 2 1 1 _ _ 2 *
            _ 1 1 1 _ _ _ _ 1 1
            _ 1 * 1 _ _ _ _ _ _
            """.trimIndent()
        )
        grid.autoRevealStartingArea(0, 9)
        solve(grid, debugPrintColumns) shouldBe true
    }
    "determine last hidden tiles based on total bomb count" {
        val grid = Grid.parse(
            """
            * 3 * 2 * 1 _ _ _ _
            * 3 1 2 1 1 _ _ _ _
            1 1 1 1 1 _ _ _ _ _
            1 1 1 * 2 1 _ _ _ _
            * 1 1 2 * 2 1 _ _ _
            1 1 _ 1 2 * 1 1 1 1
            _ _ 1 1 2 1 1 2 * 2
            _ _ 1 * 1 _ _ 3 * 4
            _ _ 1 1 1 1 1 4 * *
            _ _ _ _ _ 1 * 3 * 3
            """.trimIndent()
        )
        grid.autoRevealStartingArea(0, 9)
        solve(grid, debugPrintColumns) shouldBe true
    }
    "endgame bomb counting to avoid last 50/50 guess" {
        // TODO: find the motivation to make this test case green
        val grid = Grid.parse(
            """
            _ _ _ 1 * * 1 _ _ _
            _ _ _ 1 2 3 2 1 _ _
            _ 1 1 1 _ 1 * 1 _ _
            1 2 * 1 _ 2 2 2 _ _
            3 * 3 1 _ 1 * 3 2 1
            * * 2 _ _ 1 2 * * 2
            3 3 1 _ _ _ 1 3 * 3
            * 1 _ _ _ _ _ 2 3 *
            2 2 1 _ _ _ _ 1 * 2
            1 * 1 _ _ _ _ 1 1 1
            """.trimIndent()
        )
        grid.autoRevealStartingArea(4, 9)
        solve(grid, debugPrintColumns) shouldBe true
    }
})
