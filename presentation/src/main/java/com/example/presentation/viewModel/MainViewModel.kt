package com.example.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiResult
import com.example.domain.model.Point
import com.example.domain.useCase.getPointsUseCase.IGetPointsUseCase
import com.example.presentation.enums.NavigationItem
import com.example.presentation.ui.fragment.mainFragment.MainFragmentScreenData
import com.example.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val getPointsUseCase: IGetPointsUseCase) : BaseViewModel() {

    private val _points = MutableSharedFlow<List<Point>>()
    val points = _points.asSharedFlow()

    private var screenData = MainFragmentScreenData()

    fun getPointsCount() = screenData.pointsCount

    fun setPointsCount(pointsCount: Int) {
        screenData = screenData.copy(pointsCount = pointsCount)
    }

    fun getData() {
        viewModelScope.launch {
            showLoading()
            val data = getPointsUseCase.invoke(param = screenData.pointsCount)
            when (data) {
                is ApiResult.Error -> {
                    _failure.emit(Exception(data.message))
                }

                is ApiResult.Success<*> -> {
                    val points = data.data as List<Point>
                    _points.emit(points.sortedBy { it.x.toFloat() })
                    _navigation.emit(NavigationItem.GraphicDetails)
                }
            }
            hideLoading()
        }
    }
}
