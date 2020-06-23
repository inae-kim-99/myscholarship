package com.examplekia.myscholarship.menu.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_dashboard.*

import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.DashboardData
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_dashboard.searchBtn
import kotlinx.android.synthetic.main.fragment_dashboard.searchEdit
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    lateinit var layoutManager:LinearLayoutManager
    lateinit var adapter:MyDashboardAdapter
    lateinit var rdb:DatabaseReference
    var REQUEST_TEST = 1
    var findQuery =false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initBtn()

    }

    private fun initBtn() {
        writeBtn.setOnClickListener {
            val intent = Intent(requireContext(),WriteBoardActivity::class.java)
            startActivityForResult(intent,REQUEST_TEST)
            activity?.overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left)
        }
    }

    fun init(){
        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerView_dashboard.layoutManager = layoutManager
        rdb = FirebaseDatabase.getInstance().getReference("Dashboards")
        val query = FirebaseDatabase.getInstance().reference.child("Dashboards").limitToLast(100)
        val option = FirebaseRecyclerOptions.Builder<DashboardData>()
            .setQuery(query,DashboardData::class.java)
            .build()
        adapter = MyDashboardAdapter(option,requireContext(),activity,false)
//        adapter.itemClickListener = object : MyDashboardAdapter.OnItemClickListener{
//            override fun OnItemClick(view: View, position: Int) {
//                val intent = Intent(requireContext(),BoardActivity::class.java)
//                intent.putExtra("position",position)
//                startActivity(intent)
//            }
//        }
        recyclerView_dashboard.adapter = adapter

//        val arraySpinner = requireContext().resources.getStringArray(R.array.array_searchDashboardMenu)
//        var spinnerAdapter = ArrayAdapter(requireContext(),R.layout.item_spinner,arraySpinner)
//        spinner_dashboard.adapter = spinnerAdapter


        //검색 버튼 클릭시
        searchBtn.setOnClickListener {
            val service = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(searchEdit.windowToken,0)
            if(findQuery)
                findQueryAdapter()
            else{
                findQuery = true
                findQueryAdapter()
            }
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

    private fun findQueryAdapter(){
        if(adapter!=null){
            adapter.stopListening()
        }
        if(searchEdit.text.isEmpty()){
            val query = FirebaseDatabase.getInstance().reference.child("Dashboards").limitToLast(100)
            val option = FirebaseRecyclerOptions.Builder<DashboardData>()
                .setQuery(query,DashboardData::class.java)
                .build()
            adapter = MyDashboardAdapter(option,requireContext(),activity,false)
            recyclerView_dashboard.adapter = adapter
            adapter.startListening()
        }else{
            val query = rdb.orderByChild("name").equalTo(searchEdit.text.toString()).limitToLast(100)
            val option = FirebaseRecyclerOptions.Builder<DashboardData>()
                .setQuery(query,DashboardData::class.java)
                .build()
            adapter = MyDashboardAdapter(option,requireContext(),activity,false)
            recyclerView_dashboard.adapter = adapter
            adapter.startListening()
        }
    }


}
