package com.er453r.codingpuzzles.utils

fun <T> List<T>.findLongestSequence(): Pair<Int, Int> {
    val sequences = mutableListOf<Pair<Int, Int>>()

    for (startPos in indices) {
        for (sequenceLength in 1..(this.size - startPos) / 2) {
            var sequencesAreEqual = true

            for (i in 0 until sequenceLength)
                if (this[startPos + i] != this[startPos + sequenceLength + i]) {
                    sequencesAreEqual = false
                    break
                }

            if (sequencesAreEqual)
                sequences += Pair(startPos, sequenceLength)
        }
    }

    return sequences.maxBy { it.second }
}

fun <T> List<T>.findIndexValueInRepeatedSequence(targetIndex: Int): T {
    val bestSequence = this.findLongestSequence()
    val sequence = this.subList(bestSequence.first, bestSequence.first + bestSequence.second)
    val sequenceStartIndex = bestSequence.first
    var index = sequenceStartIndex

    while (index + sequence.size < targetIndex)
        index += sequence.size

    return sequence[targetIndex - index - 1]
}

fun <T> List<T>.combinations2(): List<Pair<T, T>> = this.indices.flatMap { n ->
    (n + 1..this.lastIndex).map { m ->
        Pair(this[n], this[m])
    }
}
