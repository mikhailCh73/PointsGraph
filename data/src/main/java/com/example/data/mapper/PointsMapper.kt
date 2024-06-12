package com.example.data.mapper

import com.example.data.model.PointsResponse
import com.example.domain.model.Point


class PointsMapper {
    fun mapPointsResponseToDomain(response: PointsResponse): List<Point> {
        return response.points.map { Point(x = it.x, y = it.y) }
    }
}
