package com.example.core.usecase

import com.example.core.data.Note

class GetWordCount {
    operator fun invoke(note: Note) : Int {
        return getCount(note.content) + getCount(note.title)
    }

    fun getCount(str: String): Int {
        return str.split(" ", "\n")
            .filter {
                it.contains(Regex("[a-zA-Z]"))
            }
            .count()
    }
}

fun main() {
    val gc = GetWordCount().getCount("this is a word 123 and not a word\n" +
            "test\n" +
            "this is a test 123 *&*^%&*^ 123233")

    println("wordcount: $gc")  // 13
}