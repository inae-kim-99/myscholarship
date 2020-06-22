package com.examplekia.myscholarship.menu.support
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.ServiceData

class MyServiceAdapter (val serviceList:ArrayList<ServiceData>, val totalCount1:Int, var totalCount2: Int, context: Context)
    :RecyclerView.Adapter<MyServiceAdapter.MyViewHolder>()
{
    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view:View, itemId:String, position:Int)
    }
    var itemClickListener: OnItemClickListener?=null

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var service_name: TextView = itemView.findViewById(R.id.service_name)
        var service_institution:TextView = itemView.findViewById(R.id.service_institution)

        init{
            itemView.setOnClickListener{
                itemClickListener?.OnItemClick(this, it, serviceList[adapterPosition].svcId,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return totalCount1+totalCount2
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.service_name.text =serviceList[position].service_name
        holder.service_institution.text =serviceList[position].service_institution
    }

}
