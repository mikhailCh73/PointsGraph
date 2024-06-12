package com.example.presentation.ui.fragment.graphicDetailsFragment


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Point
import com.example.presentation.R
import com.example.presentation.databinding.FragmentGraphicDetailsBinding
import com.example.presentation.enums.NavigationItem
import com.example.presentation.ui.fragment.base.BaseFragment
import com.example.presentation.viewModel.GraphicDetailsViewModel
import com.example.presentation.viewModel.PointsViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class GraphicDetailsFragment :
    BaseFragment<GraphicDetailsViewModel, FragmentGraphicDetailsBinding>(FragmentGraphicDetailsBinding::inflate) {
    override val viewModel: GraphicDetailsViewModel by viewModels()
    private val pointsViewModel by activityViewModels<PointsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback { findNavController().popBackStack() }
        lifecycleScope.launch {
            pointsViewModel.points.collect {
                initUI()
                initPointsTable(it)
                initPointsChart(it)
            }
        }
        lifecycleScope.launch { viewModel.isSmoothLines.collect { redrawChart(it) } }
    }

    private fun redrawChart(isSmoothLines: Boolean) {
        val mode = if (isSmoothLines) LineDataSet.Mode.HORIZONTAL_BEZIER else LineDataSet.Mode.LINEAR
        lifecycleScope.launch { pointsViewModel.points.collect { initPointsChart(it, mode) } }
    }

    private fun initUI() {
        binding.switchChartType.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setIsSmoothLines(isChecked)
        }
        binding.buttonSaveChart.setOnClickListener {
            val isGranted = checkStoragePermissions()
            if (isGranted) {
                downloadImageIntoAppFolder()
            } else {
                requestPermissionForReadWrite()
            }
        }
    }

    private fun initPointsChart(points: List<Point>, mode: LineDataSet.Mode = LineDataSet.Mode.LINEAR) {
        val entries = points.map { point -> Entry(point.x.toFloat(), point.y.toFloat()) }
        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.mode = mode
        lineDataSet.setDrawValues(false)
        val lineData = LineData(lineDataSet)
        binding.chartView.description = Description().apply { text = "" }
        binding.chartView.data = lineData
        binding.chartView.invalidate()
    }

    private fun initPointsTable(points: List<Point>) {
        binding.pointsRecyclerView.setHasFixedSize(true)
        val verticalItemDecoration = DividerItemDecoration(
            binding.pointsRecyclerView.context,
            RecyclerView.VERTICAL
        )
        val horizontalItemDecoration = DividerItemDecoration(
            binding.pointsRecyclerView.context,
            RecyclerView.HORIZONTAL
        )
        binding.pointsRecyclerView.addItemDecoration(verticalItemDecoration)
        binding.pointsRecyclerView.addItemDecoration(horizontalItemDecoration)
        binding.pointsRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), ROW_COUNT, RecyclerView.HORIZONTAL, false)
        val adapter = PointsAdapter(points)
        binding.pointsRecyclerView.adapter = adapter
    }

    private fun checkStoragePermissions(): Boolean {
        val write = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return write == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermissionForReadWrite() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ),
            READ_WRITE_STORAGE_PERMISSION_REQUEST_CODE
        )
    }


    private fun downloadImageIntoAppFolder() {
        try {
            val file = File(requireContext().getExternalFilesDir(null), "ChartImage.png")
            if (!file.exists()) file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            val bitmap = binding.chartView.chartBitmap
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            Toast.makeText(requireContext(), "Изображение сохранено в " + file.absolutePath, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.wtf("Save image exception", "$e")
        }
    }

    override fun handleNavigation() {
        lifecycleScope.launch(exceptionHandler) {
            viewModel.navigation.collect { navItem ->
                when (navItem) {
                    NavigationItem.Main -> findNavController().navigate(R.id.action_details_to_main)
                    else -> throw IllegalStateException("Unexpected route $navItem")
                }
            }
        }
    }


    companion object {
        private const val ROW_COUNT = 10
        private const val READ_WRITE_STORAGE_PERMISSION_REQUEST_CODE = 125
    }

}
