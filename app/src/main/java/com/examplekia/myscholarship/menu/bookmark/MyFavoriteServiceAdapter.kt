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
import com.examplekia.myscholarship.data.ServiceData
import com.examplekia.myscholarship.menu.support.ServiceInfoActivity

class MyFavoriteServiceAdapter(val context: Context,val favoriteList:ArrayList<ServiceData>) : RecyclerView.Adapter<MyFavoriteServiceAdapter.ViewHolder>() {

    var myDBHelper: MyDBHelper =
        MyDBHelper(context)
    //var favoriteList = myDBHelper.getFavoriteServiceList()

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name:TextView
        var institution:TextView
        var item:LinearLayout

        init{
            name = itemView.findViewById(R.id.service_name)
            institution = itemView.findViewById(R.id.service_institution)
            item = itemView.findViewById(R.id.item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_service,parent,false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var serviceData = favoriteList[position]

        holder.name.text = serviceData.service_name
        holder.institution.text = "운영기관 : "+serviceData.service_institution
        holder.item.setOnClickListener{
            val intent = Intent(context, ServiceInfoActivity::class.java)
            intent.putExtra("itemId",favoriteList[position].svcId)
            context.startActivity(intent)
        }
    }
}