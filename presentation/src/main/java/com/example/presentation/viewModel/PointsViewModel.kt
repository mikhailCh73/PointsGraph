package com.example.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointsViewModel @Inject constructor() : ViewModel() {
    private val _points = MutableStateFlow<List<Point>>(listOf())
    val points = _points.asSharedFlow()

    fun setPoints(points: List<Point>) {
        viewModelScope.launch { _points.emit(points) }
    }
}
