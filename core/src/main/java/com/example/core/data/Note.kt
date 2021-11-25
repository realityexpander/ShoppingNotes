package com.example.core.data

data class Note(
    var title: String,
    var content: String,
    var creationTime: Long,
    var updateTime: Long,
    var id: Long = 0,
    var wordCount: Int = 0
): Comparable<Note> {

    // Deep comparison for ==, <, >
    override fun compareTo(other: Note): Int {
        println("compareTo: ${this}, $other")
        return when {
            title != other.title ->
                -1
            content != other.content ->
                -1
            creationTime != other.creationTime ->
                (creationTime - other.creationTime).toInt()
            updateTime != other.updateTime ->
                (updateTime - other.updateTime).toInt()
            else -> 0
        }
    }
}