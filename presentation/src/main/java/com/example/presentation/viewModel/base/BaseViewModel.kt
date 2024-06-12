package com.example.presentation.viewModel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.enums.NavigationItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _navigation = MutableSharedFlow<NavigationItem>()
    val navigation = _navigation.asSharedFlow()

    private val _loading = MutableSharedFlow<Boolean>()
    val loading = _loading.asSharedFlow()

    protected val _failure = MutableSharedFlow<Exception>()
    internal val failure = _failure.asSharedFlow()

    protected fun showLoading() {
        viewModelScope.launch { _loading.emit(true) }
    }

    protected fun hideLoading() {
        viewModelScope.launch { _loading.emit(false) }
    }

}
