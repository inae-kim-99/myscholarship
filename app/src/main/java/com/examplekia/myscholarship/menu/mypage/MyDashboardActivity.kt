package com.examplekia.myscholarship.menu.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.DashboardData
import com.examplekia.myscholarship.menu.dashboard.MyDashboardAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_my_dashboard.*

class MyDashboardActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyDashboardAdapter
    lateinit var rdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dashboard)
        init()
    }

    private fun init() {
        val user = FirebaseAuth.getInstance().currentUser!!
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView_myDashboard.layoutManager = layoutManager

        rdb = FirebaseDatabase.getInstance().getReference("Dashboards")

        val query = rdb.orderByChild("email").equalTo(user.email).limitToLast(100)
        println(user.email)
        val option = FirebaseRecyclerOptions.Builder<DashboardData>()
            .setQuery(query,DashboardData::class.java)
            .build()
        adapter = MyDashboardAdapter(option,this, this,true)
        recyclerView_myDashboard.adapter = adapter

        adapter.startListening()

        closeBtn_myDashboard.setOnClickListener {
            this.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}