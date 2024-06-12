package com.example.presentation.ui.fragment.graphicDetailsFragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.Point
import com.example.presentation.R


class PointsAdapter(private val points: List<Point>) : RecyclerView.Adapter<ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = points.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val point = points[position]
        holder.bindViewHolder(position, point)
    }
}


class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
    private var textViewPointName: TextView? = null
    private var textViewPointValueX: TextView? = null
    private var textViewPointValueY: TextView? = null

    init {
        textViewPointName = itemView.findViewById(R.id.text_view_point_name)
        textViewPointValueX = itemView.findViewById(R.id.text_view_point_value_x)
        textViewPointValueY = itemView.findViewById(R.id.text_view_point_value_y)
    }

    fun bindViewHolder(position: Int, point: Point) {
        val pointName = "point: $position"
        val xValue = "x: ${point.x}"
        val yValue = "y: ${point.y}"
        textViewPointName?.text = pointName
        textViewPointValueX?.text = xValue
        textViewPointValueY?.text = yValue
    }
}
