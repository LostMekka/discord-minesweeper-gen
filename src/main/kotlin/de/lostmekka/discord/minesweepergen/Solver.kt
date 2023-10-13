package de.lostmekka.discord.minesweepergen

private class Area(val tiles: Set<Tile>, minBombs: Int, maxBombs: Int) {
    val minBombs = minBombs.coerceIn(0, tiles.size)
    val maxBombs = maxBombs.coerceIn(0, tiles.size)
}

// not the most efficient algorithm, but good enough for small grids.
// bigger grids will run into the discord character limit anyway, so that's a nice excuse ^^
fun solve(grid: Grid, debugPrintColumns: Int = 0): Boolean {
    val debugStrings = mutableListOf<String>()
    fun flushDebugStrings() {
        if (debugPrintColumns <= 0 || debugStrings.size <= 1) return
        println(interleavedDebugString(*debugStrings.toTypedArray()))
        println()
        val lastString = debugStrings.last()
        debugStrings.clear()
        debugStrings += lastString
    }

    fun addDebugString() {
        if (debugPrintColumns <= 0) return
        val nextDebugString = grid.debugString()
        if (nextDebugString == debugStrings.lastOrNull()) return
        debugStrings += nextDebugString
        if (debugStrings.size >= debugPrintColumns + 1) flushDebugStrings()
    }
    addDebugString()

    val activeTiles = mutableSetOf<Tile>()
    for (tile in grid) {
        if (!tile.isRevealed || tile.isBomb) continue
        val neighbours = grid.neighboursOf(tile)
        val revealedNeighbours = neighbours.count { it.isRevealed }
        if (revealedNeighbours in 1..<neighbours.size) activeTiles += tile
    }
    while (true) {
        val solvedTiles = mutableSetOf<Tile>()
        val newActiveTiles = mutableSetOf<Tile>()
        var gridChanged = false

        fun revealSafeTile(tile: Tile) {
            require(!tile.isBomb) { "revealed $tile as safe, but it was a bomb instead!" }
            gridChanged = true
            newActiveTiles += grid.reveal(tile)
        }

        fun revealBomb(tile: Tile) {
            require(tile.isBomb) { "revealed $tile as bomb, but it isn't one!" }
            gridChanged = true
            grid.reveal(tile)
        }

        val areas = mutableListOf<Area>()
        fun addArea(area: Area) {
            if (area.tiles.isNotEmpty()) areas += area
        }
        fun addArea(tiles: Set<Tile>, minBombs: Int, maxBombs: Int) = addArea(Area(tiles, minBombs, maxBombs))
        for (tile1 in activeTiles) {
            val neighbours1 = grid.neighboursOf(tile1)
            val undiscoveredTiles1 = neighbours1.filterToSet { !it.isRevealed }
            val undiscoveredBombs1 = tile1.number - neighbours1.count { it.isRevealed && it.isBomb }
            when {
                tile1.isBomb || undiscoveredTiles1.isEmpty() -> {
                    solvedTiles += tile1
                    continue
                }
                undiscoveredBombs1 == undiscoveredTiles1.size -> {
                    // all remaining unknown neighbours are bombs
                    for (n in undiscoveredTiles1) revealBomb(n)
                    solvedTiles += tile1
                    continue
                }
                undiscoveredBombs1 == 0 -> {
                    // all remaining unknown neighbours are safe
                    for (n in undiscoveredTiles1) revealSafeTile(n)
                    solvedTiles += tile1
                    continue
                }
            }
            addArea(undiscoveredTiles1, undiscoveredBombs1, undiscoveredBombs1)
            for (tile2 in grid.distantNeighboursOf(tile1)) {
                if (!tile2.isRevealed || tile2.isBomb || tile2.y < tile1.y || tile2.y == tile1.y && tile2.x <= tile1.x) continue
                val neighbours2 = grid.neighboursOf(tile2)
                val undiscoveredTiles2 = neighbours2.filterToSet { !it.isRevealed }
                val undiscoveredBombs2 = tile2.number - neighbours2.count { it.isRevealed && it.isBomb }
                val intersection = undiscoveredTiles1 intersect undiscoveredTiles2
                val only1 = undiscoveredTiles1 - intersection
                val only2 = undiscoveredTiles2 - intersection
                val intersectionArea = Area(
                    tiles = intersection,
                    minBombs = maxOf(undiscoveredBombs1 - only1.size, undiscoveredBombs2 - only2.size),
                    maxBombs = minOf(undiscoveredBombs1, undiscoveredBombs2),
                )
                if (only1.isNotEmpty() && only2.isNotEmpty()) addArea(intersectionArea)
                addArea(
                    tiles = only1,
                    minBombs = undiscoveredBombs1 - intersectionArea.maxBombs,
                    maxBombs = undiscoveredBombs1 - intersectionArea.minBombs
                )
                addArea(
                    tiles = only2,
                    minBombs = undiscoveredBombs2 - intersectionArea.maxBombs,
                    maxBombs = undiscoveredBombs2 - intersectionArea.minBombs
                )
            }
        }
        for (area in areas) {
            when {
                area.minBombs != area.maxBombs -> continue
                area.minBombs == 0 -> {
                    // no bombs left here. reveal all tiles in the area!
                    for (tile in area.tiles) revealSafeTile(tile)
                }
                area.minBombs == area.tiles.size -> {
                    // everything is bombs here. reveal all tiles in the area!
                    for (tile in area.tiles) revealBomb(tile)
                }
            }
        }

        activeTiles -= solvedTiles
        activeTiles += newActiveTiles
        addDebugString()
        if (!gridChanged) {
            val unsolvedTiles = grid.filter { !it.isRevealed }
            if (unsolvedTiles.isNotEmpty()) {
                val remainingBombCount = grid.bombCount - grid.count { it.isRevealed && it.isBomb }
                when (remainingBombCount) {
                    0 -> for (tile in unsolvedTiles) revealSafeTile(tile)
                    unsolvedTiles.size -> for (tile in unsolvedTiles) revealBomb(tile)
                }
                addDebugString()
            }
            flushDebugStrings()
            return grid.all { it.isRevealed }
        }
    }
}
