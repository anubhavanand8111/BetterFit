package com.example.betterfit.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.betterfit.ExerciseModel
import com.example.betterfit.R

class ExerciseStatusAdapter(private var exerciseList:ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_status,parent,false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       val exerciseStatus = exerciseList[position]
        holder.id.text=exerciseStatus.getId().toString()
        when {
            exerciseStatus.getIsSelected() -> {
                holder.id.setBackgroundResource(R.drawable.item_status_selected)
                holder.id.setTextColor(Color.parseColor("#212121"))
            }
            exerciseStatus.getIsCompleted() -> {
                holder.id.setBackgroundResource(R.drawable.item_status_completed)
                holder.id.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.id.setBackgroundResource(R.drawable.item_status_default)
                holder.id.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    class ItemHolder(view:View): RecyclerView.ViewHolder(view) {
        val id:TextView = view.findViewById(R.id.id)
    }
}