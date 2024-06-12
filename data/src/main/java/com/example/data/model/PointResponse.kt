package com.example.data.model

import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float,
)

data class PointsResponse(@SerializedName("points") val points: List<Point>)
