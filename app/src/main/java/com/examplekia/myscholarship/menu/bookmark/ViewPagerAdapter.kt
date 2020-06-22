package com.examplekia.myscholarship.menu.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.ServiceData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewPagerAdapter (
    var context: Context,
    var tabTitle:ArrayList<String>
): RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    var favoriteList = arrayListOf<String>()
    var favoriteServiceList = arrayListOf<ServiceData>()
    lateinit var adapter1:MyFavoriteAdapter
    lateinit var adapter2:MyFavoriteServiceAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_page, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return tabTitle.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = FirebaseAuth.getInstance().currentUser!!
        if(position == 0){
            val rdb = FirebaseDatabase.getInstance().getReference("Users")
            rdb.orderByChild("uid").equalTo(user.uid).addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(item in p0.children){
                        val name = item.child("uname").getValue().toString()
                        holder.infoText.text = name+"님의 장학금 정보입니다."
                    }

                }

            })
            //holder.infoText.text =
            rdb.child(user.uid).child("favoriteScholarList").addValueEventListener(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(i in p0.children){
                        favoriteList.add(i.getValue().toString())
                    }
                    if(favoriteList.size > 0){
                        holder.isDataText.visibility = GONE
                        holder.recyclerView_favorite.visibility = VISIBLE
                        adapter1 =
                            MyFavoriteAdapter(context,favoriteList)
                        var layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                        holder.recyclerView_favorite.layoutManager = layoutManager
                        holder.recyclerView_favorite.adapter = adapter1
                    }else{
                        holder.isDataText.visibility = VISIBLE
                        holder.recyclerView_favorite.visibility = GONE
                    }

                }

            })

        }else{
            val rdb = FirebaseDatabase.getInstance().getReference("Users")
            rdb.orderByChild("uid").equalTo(user.uid).addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(item in p0.children){
                        val name = item.child("uname").getValue().toString()
                        holder.infoText.text = name+"님의 학생지원 정보입니다."
                    }

                }

            })

            rdb.child(user.uid).child("favoriteServiceList").addValueEventListener(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
//                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {

//                    TODO("Not yet implemented")
                    for(i in p0.children){
//                        val i.
//                        favoriteServiceList.add(i.getValue())
                        val id = i.child("svcId").getValue().toString()
                        val name  = i.child("service_name").getValue().toString()
                        val ins  = i.child("service_institution").getValue().toString()
                        favoriteServiceList.add(ServiceData(id,name,ins))
                    }
                    if(favoriteServiceList.size > 0){
                        holder.isDataText.visibility = GONE
                        holder.recyclerView_favorite.visibility = VISIBLE
                        adapter2 =
                            MyFavoriteServiceAdapter(context,favoriteServiceList)
                        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                        holder.recyclerView_favorite.layoutManager = layoutManager
                        holder.recyclerView_favorite.adapter = adapter2
                    }else{
                        holder.isDataText.visibility = VISIBLE
                        holder.recyclerView_favorite.visibility = GONE
                    }

                }

            })



//            var adapter =
//                MyFavoriteServiceAdapter(
//                    context
//                )
//            var layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//            holder.recyclerView_favorite.layoutManager = layoutManager
//            holder.recyclerView_favorite.adapter = adapter
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var recyclerView_favorite: RecyclerView
        var infoText: TextView
        var isDataText:TextView
        init{
            recyclerView_favorite = itemView.findViewById(R.id.recyclerView_favorite)
            infoText = itemView.findViewById(R.id.infoText)
            isDataText = itemView.findViewById(R.id.isDataText)

        }
    }



}