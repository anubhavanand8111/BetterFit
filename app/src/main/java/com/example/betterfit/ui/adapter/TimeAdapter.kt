package com.example.betterfit.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.betterfit.R
import com.example.betterfit.db.TimeData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TimeAdapter(private var timeList: ArrayList<TimeData>):RecyclerView.Adapter<TimeAdapter.ItemHolder>() {

    class ItemHolder(view: View):RecyclerView.ViewHolder(view) {
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
     val timeStatus = timeList[position]
        val convertedTime = timeStatus.time
        val sdf = SimpleDateFormat("MMM dd,yyyy hh:mm aa")
        val resultDate  = Date(convertedTime)
        holder.tvDate.text = sdf.format(resultDate).toString()
    }

    override fun getItemCount(): Int {
        return timeList.size
    }
}