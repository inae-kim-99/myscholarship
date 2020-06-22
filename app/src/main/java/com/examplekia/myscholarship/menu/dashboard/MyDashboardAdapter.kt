package com.examplekia.myscholarship.menu.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.DashboardData
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase


class MyDashboardAdapter(options:FirebaseRecyclerOptions<DashboardData>,val context: Context,val activity:FragmentActivity?,val isBtn:Boolean) :
    FirebaseRecyclerAdapter<DashboardData, MyDashboardAdapter.ViewHolder>(options) {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name_board:TextView
        var content_board:TextView
        var date_board:TextView
        var item:LinearLayout
        var deleteBtn:TextView

        init{
            name_board = itemView.findViewById(R.id.name_board)
            content_board = itemView.findViewById(R.id.content_board)
            date_board = itemView.findViewById(R.id.date_board)
            item = itemView.findViewById(R.id.item)
            deleteBtn = itemView.findViewById(R.id.deleteBtn)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: DashboardData) {
        holder.name_board.text = model.name
        holder.content_board.text=model.content
        holder.date_board.text=model.date
        if(isBtn){
            holder.deleteBtn.visibility = VISIBLE
            holder.deleteBtn.setOnClickListener {
                val rdb = FirebaseDatabase.getInstance().getReference("Dashboards")
                rdb.child(model.key).removeValue()
                this.notifyDataSetChanged()
            }
        }else{
            holder.deleteBtn.visibility = GONE
        }
        holder.item.setOnClickListener {
            val intent = Intent(context,BoardActivity::class.java)
            intent.putExtra("key",model.key)
            println("put key : "+model.key)
            context.startActivity(intent)
            activity?.overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left)
        }
    }


}