package com.example.presentation.ui.fragment.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.presentation.R
import com.example.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {


    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    private val progressView by lazy { layoutInflater.inflate(R.layout.progress_view, null) }

    @Inject
    lateinit var exceptionHandler: CoroutineExceptionHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleNavigation()
        if (savedInstanceState == null) initProgressBar(view)
        lifecycleScope.launch {
            viewModel.loading.collect { isLoading -> if (isLoading) showLoading() else hideLoading() }
        }
        lifecycleScope.launch { viewModel.failure.collect { error -> handleFailure(error) } }
    }

    private fun initProgressBar(view: View) {
        val layout = view.findViewById<ConstraintLayout>(R.id.root_container)
        if (progressView.parent != null) (progressView.parent as ViewGroup).removeView(progressView)
        layout.addView(progressView)
        progressView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            width = LayoutParams.MATCH_PARENT
            height = LayoutParams.MATCH_PARENT
        }
        progressView.isVisible = false
        progressView.setOnClickListener { }
    }

    abstract fun handleNavigation()

    private fun handleFailure(e: Exception) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder
            .setTitle(R.string.error_dialog_title)
            .setMessage(R.string.error_dialog_message)
            .setPositiveButton(R.string.error_dialog_ok) { d, _ -> d.dismiss() }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showLoading() {
        progressView.isVisible = true
    }

    private fun hideLoading() {
        progressView.isVisible = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
