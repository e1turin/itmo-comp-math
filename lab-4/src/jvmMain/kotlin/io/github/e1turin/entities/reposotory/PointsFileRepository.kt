package io.github.e1turin.entities.reposotory

import io.github.e1turin.entities.point.Point
import java.io.File

interface Repository<T, D> {
    fun loadFrom(dest: D): T
    fun saveTo(dest: D, data: T)
}

interface PointsRepository<D> : Repository<Array<Point>, D> {
    override fun loadFrom(dest: D): Array<Point>
    override fun saveTo(dest: D, data: Array<Point>)
}

interface PointsFileRepository : PointsRepository<File> {
    override fun loadFrom(dest: File): Array<Point>
    override fun saveTo(dest: File, data: Array<Point>)
}