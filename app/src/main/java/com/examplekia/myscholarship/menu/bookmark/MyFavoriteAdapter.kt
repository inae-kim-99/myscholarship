package com.examplekia.myscholarship.menu.bookmark

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.MyDBHelper
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.MyData
import com.examplekia.myscholarship.menu.scholarship.InfoActivity

class MyFavoriteAdapter(val context: Context,val favoriteList:ArrayList<String>) : RecyclerView.Adapter<MyFavoriteAdapter.ViewHolder>() {

    var myDBHelper: MyDBHelper =
        MyDBHelper(context)

    //var favoriteList = myDBHelper.getFavoriteList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname:TextView
        var pinstitution:TextView
        var papplydate:TextView
        var item:LinearLayout

        init{
            pname = itemView.findViewById(R.id.pname)
            pinstitution = itemView.findViewById(R.id.pinstitution)
            papplydate = itemView.findViewById(R.id.papplydate)
            item = itemView.findViewById(R.id.item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite,parent,false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data:MyData?
        data = myDBHelper.showItem(favoriteList[position])

        holder.pname.text = data?.PNAME
        holder.pinstitution.text = "운영기관 : "+data?.PINSTITUTION
        holder.papplydate.text = "모집기간 : "+data?.PAPPLY_DATE

        holder.item.setOnClickListener {
            val intent = Intent(context, InfoActivity::class.java)
            intent.putExtra("position", favoriteList[position])
            context.startActivity(intent)
        }
    }
}