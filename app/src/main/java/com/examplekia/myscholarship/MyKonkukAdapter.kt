package com.examplekia.myscholarship
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.data.KonkukData

class MyKonkukAdapter (val items:ArrayList<KonkukData>,context: Context)
    :RecyclerView.Adapter<MyKonkukAdapter.MyViewHolder>()
{
    interface OnItemClickListener{
        fun OnItemClick(holder:MyViewHolder, view:View, data:KonkukData, position:Int)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var title: TextView = itemView.findViewById(R.id.title)
        var date:TextView = itemView.findViewById(R.id.date)
        var views:TextView = itemView.findViewById(R.id.views)

        init{
            itemView.setOnClickListener{
                itemClickListener?.OnItemClick(this, it, items[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_konkuk, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.date.text = items[position].date
        holder.views.text = items[position].views
//        holder.itemView.setOnClickListener {
//            val openURL = Intent(Intent.ACTION_VIEW)
//            openURL.data = Uri.parse(items.get(position).url)
//            startActivity(context,openURL,null)
//        }
    }

}
