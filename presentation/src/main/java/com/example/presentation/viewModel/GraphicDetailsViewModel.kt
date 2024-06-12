package com.example.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphicDetailsViewModel @Inject constructor() : BaseViewModel() {

    private val _isSmoothLines = MutableSharedFlow<Boolean>()
    val isSmoothLines = _isSmoothLines.asSharedFlow()

    fun setIsSmoothLines(isSmoothLines: Boolean) {
        viewModelScope.launch { _isSmoothLines.emit(isSmoothLines) }
    }
}
