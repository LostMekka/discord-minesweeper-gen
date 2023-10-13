package de.lostmekka.discord.minesweepergen

import java.util.LinkedList

class Grid(
    val width: Int,
    private val tiles: Array<Tile>,
) : Iterable<Tile> {
    init {
        require(tiles.size % width == 0)
    }

    val height = tiles.size / width
    val bombCount = tiles.count { it.isBomb }

    override operator fun iterator() = tiles.iterator()

    fun isInside(x: Int, y: Int): Boolean {
        return x in 0..<width && y in 0..<height
    }

    operator fun get(x: Int, y: Int): Tile {
        return tiles[idOf(x, y)]
    }

    private fun update(tile: Tile): Tile {
        tiles[tile.id] = tile
        return tile
    }

    fun reveal(tile: Tile): Tile {
        if (tile.isRevealed) return tile
        return update(tile.copy(isRevealed = true))
    }

    private val neighborIndexCache by lazy { neighborCache(maxDistance = 1) }
    private val distantNeighborIndexCache by lazy { neighborCache(maxDistance = 2) }
    fun neighboursOf(tile: Tile) = neighborIndexCache[tile.id].map { tiles[it] }
    fun distantNeighboursOf(tile: Tile) = distantNeighborIndexCache[tile.id].map { tiles[it] }

    private fun neighborCache(maxDistance: Int) = Array(tiles.size) {
        val x = it % width
        val y = it / width
        val list = buildList {
            for (dx in -maxDistance..maxDistance) {
                for (dy in -maxDistance..maxDistance) {
                    val nx = x + dx
                    val ny = y + dy
                    if (dx == 0 && dy == 0 || !isInside(nx, ny)) continue
                    add(idOf(nx, ny))
                }
            }
        }
        list.toIntArray()
    }

    fun copy(): Grid = Grid(width, tiles.map { it.copy() }.toTypedArray())

    fun debugString(revealed: Boolean = false) = buildString {
        for (y in 0..<height) {
            for (x in 0..<width) {
                val tile = this@Grid[x, y]
                append(
                    when {
                        !revealed && !tile.isRevealed -> "#"
                        tile.isBomb -> "*"
                        tile.number == 0 -> "_"
                        else -> tile.number.toString()
                    }
                )
                if (x != width - 1) append(" ")
            }
            if (y != height - 1) appendLine()
        }
    }

    fun discordString() = buildString {
        for (y in 0..<height) {
            for (x in 0..<width) {
                val tile = this@Grid[x, y]
                val element = when {
                    tile.isBomb -> "bomb"
                    tile.number == 0 -> "zero"
                    tile.number == 1 -> "one"
                    tile.number == 2 -> "two"
                    tile.number == 3 -> "three"
                    tile.number == 4 -> "four"
                    tile.number == 5 -> "five"
                    tile.number == 6 -> "six"
                    tile.number == 7 -> "seven"
                    tile.number == 8 -> "eight"
                    else -> error("invalid state of tile $tile")
                }
                append(if (tile.isRevealed) ":$element:" else "||:$element:||")
            }
            if (y != height - 1) appendLine()
        }
    }

    fun autoRevealStartingArea(x: Int, y: Int) = autoRevealStartingArea(this[x, y])
    fun autoRevealStartingArea(startingTile: Tile) {
        reveal(startingTile)
        if (startingTile.number != 0) return
        val zeroes = LinkedList<Tile>().apply { add(startingTile) }
        while (zeroes.isNotEmpty()) {
            val tile = zeroes.removeFirst()
            for (n in neighboursOf(tile)) {
                if (n.isRevealed) continue
                reveal(n)
                if (n.number == 0) zeroes.addLast(n)
            }
        }
    }

    private fun idOf(x: Int, y: Int) = x + y * width

    companion object {
        fun generate(width: Int, height: Int, bombCount: Int): Grid {
            val size = width * height
            val bombIndices = (0..<size).sample(bombCount).toSet()
            val tiles = Array(size) {
                Tile(
                    id = it,
                    x = it % width,
                    y = it / width,
                    number = if (it in bombIndices) -1 else 0,
                    isRevealed = false,
                )
            }
            val grid = Grid(width, tiles)
            grid.updateNumbers()
            grid.revealStartingArea()
            return grid
        }

        private fun Grid.updateNumbers() {
            for (tile in this) {
                if (tile.isBomb) continue
                val adjacentBombCount = neighboursOf(tile).count { it.isBomb }
                update(tile.copy(number = adjacentBombCount))
            }
        }

        private fun Grid.revealStartingArea() {
            // TODO: handle case where there are no zero tiles
            val firstZeroTile = filter { it.number == 0 }.random()
            autoRevealStartingArea(firstZeroTile)
        }

        fun parse(input: String): Grid {
            val lines = input.lines().map { it.trim().split(" ") }
            val width = lines.first().size
            val height = lines.size
            require(lines.all { it.size == width }) { "cannot parse input: lines aren't the same length\n$input" }
            val tiles = Array(width * height) {
                val x = it % width
                val y = it / width
                val element = lines[y][x]
                Tile(
                    id = it,
                    x = x,
                    y = y,
                    number = when (element) {
                        "_" -> 0
                        "*" -> -1
                        else -> element.toInt()
                    },
                    isRevealed = false,
                )
            }
            return Grid(width, tiles)
        }
    }
}
