package io.github.e1turin.entities.reposotory

import io.github.e1turin.entities.point.Point
import java.io.File

interface Repository<T, D> {
    fun loadFrom(dest: D): T
    fun saveTo(dest: D, data: T)
}

interface PointsRepository<D> : Repository<List<Point>, D> {
    override fun loadFrom(dest: D): List<Point>
    override fun saveTo(dest: D, data: List<Point>)
}

interface PointsFileRepository : PointsRepository<File> {
    override fun loadFrom(dest: File): List<Point>
    override fun saveTo(dest: File, data: List<Point>)
}