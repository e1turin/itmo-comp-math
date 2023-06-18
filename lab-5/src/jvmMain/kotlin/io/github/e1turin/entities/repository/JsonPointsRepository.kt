package io.github.e1turin.entities.repository

import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.config.json.jsonFormat
import io.github.e1turin.shared.lib.cringe.notation.DelicateCringeApi
import io.github.e1turin.shared.lib.cringe.notation.fromCringeFormat
import io.github.e1turin.shared.lib.cringe.notation.toCringeFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@OptIn(DelicateCringeApi::class)
object JsonPointsRepository {
    fun loadFrom(dest: File): List<Point> {
        val json = dest.readText().fromCringeFormat()
        return Json.decodeFromString<List<Point>>(json)
    }

    fun saveTo(dest: File, data: List<Point>) {
        val con = jsonFormat.encodeToString(data).toCringeFormat()
        dest.writeText(con)
    }
}
