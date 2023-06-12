package io.github.e1turin.entities.reposotory

import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.lib.cringe.DelicateCringeApi
import io.github.e1turin.shared.lib.cringe.fromCringeFormat
import io.github.e1turin.shared.lib.cringe.toCringeFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@OptIn(DelicateCringeApi::class)
object JsonPointsRepository : PointsFileRepository {
    override fun loadFrom(dest: File): List<Point> {
        val json = dest.readText().fromCringeFormat()
        return Json.decodeFromString<List<Point>>(json)
    }

    override fun saveTo(dest: File, data: List<Point>) {
        val con = jsonFormat.encodeToString(data).toCringeFormat()
        dest.writeText(con)
    }
}

val jsonFormat = Json { prettyPrint = true }