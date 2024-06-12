package com.example.presentation.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSplashBinding
import com.example.presentation.enums.NavigationItem
import com.example.presentation.ui.fragment.base.BaseFragment
import com.example.presentation.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override val viewModel: SplashViewModel by viewModels()


    override fun handleNavigation() {
        lifecycleScope.launch(exceptionHandler) {
            viewModel.navigation.collect { navItem ->
                when (navItem) {
                    NavigationItem.Main -> findNavController().navigate(R.id.action_splash_to_main)
                    else -> throw IllegalStateException("Unexpected route $navItem")
                }
            }
        }
    }

}