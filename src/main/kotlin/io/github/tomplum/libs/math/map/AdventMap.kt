package io.github.tomplum.libs.math.map

import io.github.tomplum.libs.math.point.Point

/**
 * This class is designed for inheritance.
 *
 * Lots of the days involve the concept of a 'Map' or a 'Maze' which can be represented as a cartesian grid.
 * A cartesian-style grid is internally maintained that maps tiles to [Point] coordinates.
 * Several sub-type abstract classes exist mapping the different dimensions against the tiles.
 *
 * @param T The type of [MapTile] that will be recorded in the grid.
 */
abstract class AdventMap<P: Point, T: MapTile<*>> {
    /** The internal data representation, mapping the positions to the tiles */
    protected val data = mutableMapOf<P, T>()

    /**
     * Adds a new [tile] at the given [position].
     * If a tile already exists at the given position then it returned, otherwise null.
     */
    protected fun addTile(position: P, tile: T): T? = data.put(position, tile)

    /**
     * Retrieves the tile at the given [position].
     * @param position The point where the target tile is located.
     * @throws IllegalArgumentException if the map does not contain a tile at the given [position].
     * @return The tile [T], if it exists.
     */
    protected fun getTile(position: Point): T {
        return data[position] ?: throw IllegalArgumentException("Map does not contain tile at $position")
    }

    /**
     * Retrieves the tile at the given [position].
     * If there is no tile present then the [default] is returned.
     * @param position The point where the target tile is located.
     * @param default The tile to be returned should there nothing recorded at the given [position].
     * @return The tile [T].
     */
    protected fun getTile(position: Point, default: T): T = data.getOrDefault(position, default)

    /**
     * Removes the tile at the given [position].
     * @param position The position of the tile.
     */
    protected fun removeTile(position: P): T? = data.remove(position)

    /**
     * Checks to see if the map currently has a tile recorded at the given [position].
     * @param position The position to check at.
     * @return true if a tile is present, else false.
     */
    protected fun hasRecorded(position: P): Boolean = data.containsKey(position)

    /**
     * Checks if the map has a tile of the given type. Equality is left up to the [MapTile].
     * @param value The tile to check for.
     * @return true if the map has recorded at least one tile with the given [value]
     */
    protected fun hasTile(value: T): Boolean = data.containsValue(value)

    /**
     * Gets all the tiles at the given [positions]. If there is no recorded at tile at one of the given [positions]
     * then it is omitted from the response.
     * @param positions The positions to find.
     * @return a [Map] of the given [positions] and their respective tiles.
     */
    protected fun filterPoints(positions: Set<P>): Map<P, T> {
        return positions.filter(this::hasRecorded).associateWith(this::getTile)
    }

    /**
     * Gets all the tiles that equate to true against the given [predicate].
     * @param predicate Each implementation of [AdventMap] will have a tile of type [T]. This tile will provide the
     * function that will be evaluated
     * @return a [Map] of all tiles that match the given [predicate].
     */
    protected fun filterTiles(predicate: (T) -> Boolean): Map<P, T> = data.filterValues(predicate)

    /**
     * Gets all the tiles that are adjacent to the given [positions].
     * @param positions The set of positions whose adjacent tiles will be found.
     * @return a [Map] of adjacent [Point] and their respective tiles [T].
     */
    protected fun adjacentTiles(positions: Set<P>): Map<Point, T> {
        return positions.flatMap { pos -> pos.adjacent() }.associateWith { adj -> getTile(adj) }
    }

    /**
     * Gets all the tiles that are adjacent to the given [positions].
     * Any positions that are not recorded in the map will have a null value paired.
     * @param positions The set of positions whose adjacent tiles will be found.
     * @param default The value of [T] to return if one of the adjacent tiles is not found in the map.
     * @return a [Map] of adjacent [Point] and their respective tiles [T].
     */
    protected fun adjacentTiles(positions: Set<Point>, default: T?): Map<Point, T?> {
        return positions.flatMap { pos -> pos.adjacent() }.associateWith { adj -> data[adj] ?: default }
    }

    /**
     * Calculates the number of tiles in the map.
     * @return The number of tile currently recorded in the [AdventMap].
     */
    protected fun tileQuantity(): Int = data.size

    /**
     * Resets the map as if it is a new instance of [AdventMap].
     * All internally stored data is cleared.
     */
    fun reset() = data.clear()

    /**
     * Two [AdventMap]s are equal to one another if they have the same [data].
     */
    override fun equals(other: Any?): Boolean {
        if (other !is AdventMap<*, *>) return false
        return this.data == other.data
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

}