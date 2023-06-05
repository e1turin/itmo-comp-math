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
    override fun loadFrom(dest: File): Array<Point> {
        val json = dest.readText().fromCringeFormat()
        return Json.decodeFromString<Array<Point>>(json)
    }

    override fun saveTo(dest: File, data: Array<Point>) {
        val con = Json.encodeToString(data).toCringeFormat()
        dest.writeText(con)
    }
}