package com.examplekia.myscholarship.tutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.R

class TutorialAdapter (var context: Context): RecyclerView.Adapter<TutorialAdapter.ViewHolder>()
{
    val page_list = arrayOf(
        R.drawable.slide1,
        R.drawable.slide2,
        R.drawable.slide3,
        R.drawable.slide4
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.tutorial_page, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return page_list.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(page_list[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imageView: ImageView
        init{
            imageView =itemView.findViewById(R.id.image_view)
        }
    }

}