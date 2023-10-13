package de.lostmekka.discord.minesweepergen

fun main() {
    generateSingleGrid(10, 10, 15)
    // findUnsolvable(10, 10, 15)
}

fun findUnsolvable(width: Int, height: Int, bombCount: Int) {
    while (true) {
        val grid = Grid.generate(width, height, bombCount)
        val copy = grid.copy()
        val solved = solve(copy)
        if (!solved) {
            println("=========== NOT SOLVABLE =============")
            println(
                interleavedDebugString(
                    grid.debugString(),
                    copy.debugString(),
                    grid.debugString(revealed = true),
                )
            )
            println()
            println(grid.discordString())
            break
        }
    }
}

private fun generateSingleGrid(width: Int, height: Int, bombCount: Int) {
    val grid = Grid.generate(width, height, bombCount)
    val copy = grid.copy()
    val solved = solve(copy, debugPrintColumns = 5)
    if (solved) {
        println("=========== SOLVABLE =============")
        println(
            interleavedDebugString(
                grid.debugString(),
                copy.debugString(),
            )
        )
    } else {
        println("=========== NOT SOLVABLE =============")
        println(
            interleavedDebugString(
                grid.debugString(),
                copy.debugString(),
                grid.debugString(revealed = true),
            )
        )
    }
    println()
    println(grid.discordString())
}
