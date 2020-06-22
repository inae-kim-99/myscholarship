package com.examplekia.myscholarship.menu.scholarship

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.examplekia.myscholarship.MyDBHelper
import com.examplekia.myscholarship.MyListAdapter

import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.FilterData
import kotlinx.android.synthetic.main.fragment_scholar.*
import kotlinx.android.synthetic.main.fragment_scholar.recyclerView
import kotlinx.android.synthetic.main.fragment_scholar.searchBtn
import kotlinx.android.synthetic.main.fragment_scholar.searchEdit
import kotlinx.android.synthetic.main.fragment_scholar.spinner_search
import java.io.FileOutputStream

/**
 * A simple [Fragment] subclass.
 */
class ScholarFragment : Fragment() {

    lateinit var myDBHelper: MyDBHelper
    var filterData = FilterData()
    var resultSearchList:ArrayList<String>? = arrayListOf<String>()//pnum
    var REQUEST_TEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scholar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myDBHelper = MyDBHelper(requireContext())
//        val dbfile = requireContext().getDatabasePath("mydb.db")
//        if(dbfile.exists()){
//            dataBtnLayout.visibility = GONE
//            recyclerView.visibility= VISIBLE
//        }
        init()
    }

    private fun init() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MyListAdapter(null,requireContext(),activity)

        //필터 버튼 클릭시
        filterBtn.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivityForResult(intent,REQUEST_TEST)
        }

        //검색 버튼 클릭시
        searchBtn.setOnClickListener {
            recyclerView.visibility = VISIBLE
            scholarship_empty.visibility = GONE

            //검색어
            if(searchEdit.text.isEmpty()){
                filterData.searchText = null
            }else{
                filterData.searchText = searchEdit.text.toString()
            }
            //검색항목
            filterData.searchType = spinner_search.selectedItem.toString()

            resultSearchList = myDBHelper.searchItems(filterData)
            if(resultSearchList?.size == 0){
                recyclerView.visibility = GONE
                scholarship_empty.visibility = VISIBLE
            }
            recyclerView.adapter = MyListAdapter(resultSearchList,requireContext(),activity)

        }

        val arraySpinner = requireContext().resources.getStringArray(R.array.array_searchMenu)
        var spinnerAdapter = ArrayAdapter(requireContext(),R.layout.item_spinner,arraySpinner)
        spinner_search.adapter = spinnerAdapter

        //searchBtn.callOnClick()
        MyAsyncTask().execute()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_TEST){
            if(resultCode == Activity.RESULT_OK){
                val filter = data?.getSerializableExtra("filterData") as FilterData
                filterData.isFilter = true
                filterData.pd_list = filter.pd_list
                filterData.pinstitutiond_list = filter.pinstitutiond_list
                filterData.pscholarshipd_list = filter.pscholarshipd_list
                filterData.pgrade_list = filter.pgrade_list
                filterData.puniv_list = filter.puniv_list
            }
        }

    }

    private fun initDB() {
        val dbfile = requireContext().getDatabasePath("mydb.db")
        if(!dbfile.parentFile.exists()){
            dbfile.parentFile.mkdir()
        }
        if(!dbfile.exists()){
            val file = resources.openRawResource(R.raw.mydb)
            val fileSize = file.available()
            val buffer =ByteArray(fileSize)
            file.read(buffer)
            file.close()
            dbfile.createNewFile()
            val output = FileOutputStream(dbfile)
            output.write(buffer)
            output.close()
            myDBHelper.setDB()
        }
    }

    inner class MyAsyncTask: AsyncTask<String, String, String>(){

        lateinit var progressDialog : ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.show()
            progressDialog.setMessage("장학금 정보를 불러오는 중입니다. 한 번만 소요되니 잠시만 기다려 주세요...")
        }

        override fun doInBackground(vararg params: String?): String {
            initDB()
            return "test"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()
            //dataBtn.visibility = GONE
            recyclerView.visibility = VISIBLE
        }
    }

}
