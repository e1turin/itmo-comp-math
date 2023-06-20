package io.github.e1turin.entities.repository

import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.lib.cringe.notation.toCringeFormat
import kotlinx.serialization.encodeToString
import java.io.File

object FDRepository {
//    fun loadFrom(dest: File): List<Point> {
//        val json = dest.readText().fromCringeFormat()
//        return Json.decodeFromString<List<Point>>(json)
//    }

    fun saveTo(dest: File, data: String) {
        dest.writeText(data)
    }
}