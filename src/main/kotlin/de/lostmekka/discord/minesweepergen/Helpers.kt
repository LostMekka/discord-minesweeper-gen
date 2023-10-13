package de.lostmekka.discord.minesweepergen

import kotlin.random.Random

fun <T> Iterable<T>.sample(amount: Int): List<T> {
    val samples = mutableListOf<T>()
    for ((i, element) in this.withIndex()) {
        if (i < amount) {
            samples.add(element)
        } else {
            val targetIndex = Random.nextInt(i + 1)
            if (targetIndex < amount) samples[targetIndex] = element
        }
    }
    return samples
}

fun <T> Iterable<T>.filterToSet(predicate: (T) -> Boolean): Set<T> = filterTo(mutableSetOf(), predicate)

fun interleavedDebugString(vararg debugStrings: String): String {
    val lines = debugStrings.map { it.lines() }
    val lineCount = lines.first().size
    require(lines.all { it.size == lineCount }) { "cannot interleave strings of different line counts" }
    return (0..<lineCount).joinToString("\n") { i ->
        lines.joinToString("  >>  ") { it[i] }
    }
}
