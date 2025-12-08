package com.er453r.codingpuzzles.utils

/**
 * A generic Disjoint Set Union (DSU) data structure.
 *
 * @param T The type of elements stored in the sets.
 */
class DisjointSet<T> {
    constructor(list: List<T>){
        list.forEach { makeSet(it) }
    }

    // Maps an element to its parent.
    // If parent[x] == x, then x is the representative (root) of the set.
    private val parent = mutableMapOf<T, T>()

    // Maps a representative element to the approximate depth (rank) of its tree.
    // Used to optimize the union operation by keeping trees flat.
    private val rank = mutableMapOf<T, Int>()

    // Optional: specific track of set sizes if needed
    val size = mutableMapOf<T, Int>()

    /**
     * Registers a new element into the DSU as its own set.
     * Does nothing if the element already exists.
     */
    private fun makeSet(item: T) {
        if (parent.containsKey(item)) return
        parent[item] = item
        rank[item] = 0
        size[item] = 1
    }

    /**
     * Finds the representative (root) of the set containing [item].
     * Implements Path Compression.
     */
    fun find(item: T): T {
        if (!parent.containsKey(item)) {
            throw IllegalArgumentException("Item $item not found in DisjointSet.")
        }

        // Path Compression:
        // If the current item is not the root, recursively find the root
        // and update the current item's parent to point directly to the root.
        if (parent[item] != item) {
            parent[item] = find(parent[item]!!)
        }
        return parent[item]!!
    }

    /**
     * Merges the sets containing [item1] and [item2].
     * Implements Union by Rank.
     * @return true if a merge happened, false if they were already in the same set.
     */
    fun union(item1: T, item2: T): Boolean {
        val root1 = find(item1)
        val root2 = find(item2)

        if (root1 == root2) return false // Already in the same set

        // Union by Rank: Attach the shorter tree to the taller tree
        val rank1 = rank[root1] ?: 0
        val rank2 = rank[root2] ?: 0

        if (rank1 < rank2) {
            parent[root1] = root2
            size[root2] = (size[root2] ?: 1) + (size[root1] ?: 1)
        } else if (rank1 > rank2) {
            parent[root2] = root1
            size[root1] = (size[root1] ?: 1) + (size[root2] ?: 1)
        } else {
            // If ranks are same, arbitrary choice (attach 2 to 1) and increment rank
            parent[root2] = root1
            size[root1] = (size[root1] ?: 1) + (size[root2] ?: 1)
            rank[root1] = rank1 + 1
        }
        return true
    }

    /**
     * Checks if two items are in the same set.
     */
    fun connected(item1: T, item2: T): Boolean {
        return find(item1) == find(item2)
    }

    /**
     * Returns the size of the set that [item] belongs to.
     */
    fun getSetSize(item: T): Int {
        val root = find(item)
        return size[root] ?: 1
    }
}
