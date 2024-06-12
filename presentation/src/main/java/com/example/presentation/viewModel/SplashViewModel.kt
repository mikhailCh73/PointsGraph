package com.example.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.example.presentation.enums.NavigationItem
import com.example.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    init {
        viewModelScope.launch {
            showLoading()
            delay(2000)
            hideLoading()
            _navigation.emit(NavigationItem.Main)
        }
    }
}