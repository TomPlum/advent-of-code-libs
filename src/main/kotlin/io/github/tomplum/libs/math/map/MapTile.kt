package io.github.tomplum.libs.math.map

//TODO: Add abstract methods for isTraversable(), isWall() etc.. for common path-finding functions
open class MapTile<T>(open val value: T) {
    override fun equals(other: Any?): Boolean {
        if (other !is MapTile<*>) return false
        return this.value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString() = value.toString()
}