package com.example.presentation.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.example.presentation.R
import com.example.presentation.ui.activity.base.BaseActivity
import com.example.presentation.viewModel.PointsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val pointsViewModel: PointsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
