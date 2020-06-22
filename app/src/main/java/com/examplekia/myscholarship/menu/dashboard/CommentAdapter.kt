package com.examplekia.myscholarship.menu.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.CommentData
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class CommentAdapter(options:FirebaseRecyclerOptions<CommentData>,val context: Context) :
    FirebaseRecyclerAdapter<CommentData, CommentAdapter.ViewHolder>(options) {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nickname:TextView
        var date:TextView
        var content:TextView
        var item:LinearLayout

        init{
            nickname = itemView.findViewById(R.id.nickname_comment)
            date = itemView.findViewById(R.id.date_comment)
            content = itemView.findViewById(R.id.content_comment)
            item = itemView.findViewById(R.id.item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: CommentData) {
        holder.nickname.text = model.writer
        holder.date.text=model.date
        holder.content.text=model.content
        println("댓글 ㄴ애용 : "+model.content)
    }


}