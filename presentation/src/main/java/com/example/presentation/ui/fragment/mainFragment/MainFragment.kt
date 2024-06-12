package com.example.presentation.ui.fragment.mainFragment


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.FragmentMainBinding
import com.example.presentation.enums.NavigationItem
import com.example.presentation.ui.fragment.base.BaseFragment
import com.example.presentation.viewModel.MainViewModel
import com.example.presentation.viewModel.PointsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(FragmentMainBinding::inflate) {
    override val viewModel: MainViewModel by viewModels()
    private val pointsViewModel by activityViewModels<PointsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback { }
        initUI()
        initBusinessLogic()
    }

    override fun handleNavigation() {
        lifecycleScope.launch(exceptionHandler) {
            viewModel.navigation.collect { navItem ->
                when (navItem) {
                    NavigationItem.GraphicDetails -> findNavController().navigate(R.id.action_main_to_details)
                    else -> throw IllegalStateException("Unexpected route $navItem")
                }
            }
        }
    }


    private fun initUI() {
        if (viewModel.getPointsCount() != 0) binding.editTextPointsCount.setText(viewModel.getPointsCount().toString())
        binding.btnNext.setOnClickListener { viewModel.getData() }
        binding.editTextPointsCount.addTextChangedListener {
            try {
                viewModel.setPointsCount(Integer.parseInt(it.toString()))
            } catch (e: Exception) {
                viewModel.setPointsCount(0)
            }
            updateButtonState()
        }

    }

    private fun updateButtonState() {
        binding.btnNext.isEnabled = viewModel.getPointsCount() != 0
    }

    private fun initBusinessLogic() {
        lifecycleScope.launch { viewModel.points.collect(pointsViewModel::setPoints) }
    }

}
