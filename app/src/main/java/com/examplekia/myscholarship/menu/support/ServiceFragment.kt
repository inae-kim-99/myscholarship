package com.examplekia.myscholarship.menu.support

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.MyDBHelper
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.ServiceData
import kotlinx.android.synthetic.main.fragment_scholar.*
import kotlinx.android.synthetic.main.fragment_service.*
import kotlinx.android.synthetic.main.fragment_service.searchBtn
import kotlinx.android.synthetic.main.fragment_service.searchEdit
import kotlinx.android.synthetic.main.fragment_service.spinner_search
import org.jsoup.Jsoup

/**
 * A simple [Fragment] subclass.
 */
class ServiceFragment : Fragment() {

    var str = ""
    var serviceList= arrayListOf<ServiceData>()
    val url1 = "http://api.korea.go.kr/openapi/svc/list?format=xml&serviceKey=P5pThUxFlhq9WgbVUacTrkmFQEQLzEOKjyHIzVLgAPHRuc79fx7W1abgUdFWgzZQXhJG7EIPCVveFHxNyW3bEA%3D%3D&lrgAstCd=060000&mdmAstCd=060700&smallAstCd=060701"
    val url2 = "http://api.korea.go.kr/openapi/svc/list?format=xml&serviceKey=P5pThUxFlhq9WgbVUacTrkmFQEQLzEOKjyHIzVLgAPHRuc79fx7W1abgUdFWgzZQXhJG7EIPCVveFHxNyW3bEA%3D%3D&lrgAstCd=060000&mdmAstCd=060700&smallAstCd=060703"
    var totalCount1=0
    var totalCount2=0
    lateinit var myDBHelper:MyDBHelper
    lateinit var progressDialog2 :ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun getPageInfo(pageIndex:Int,pageSize:Int):String{
        return "&pageIndex"+pageIndex.toString()+"&pageSize="+pageSize.toString()
    }

    private fun init() {
        myDBHelper = MyDBHelper(requireContext())
        MyAsyncTask().execute(url1,url2)

        //검색 버튼 클릭
        searchBtn.setOnClickListener {
            if(!searchEdit.text.isEmpty()){
                val service = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                service.hideSoftInputFromWindow(searchEdit.windowToken,0)
                MyAsyncTask2().execute()
//                var searchText = searchEdit.text.toString()
//                var tempList = arrayListOf<ServiceData>()
//                when(spinner_search.selectedItem.toString()){
//                    "전체"->{//전체
//                        for(i in serviceList){
//                            if(i.service_name.contains(searchText) || i.service_institution.contains(searchText)){
//                                tempList.add(i)
//                            }
//                        }etr
//                    }
//                    "제목"->{//제목
//                        for(i in serviceList){
//                            if(i.service_name.contains(searchText)){
//                                tempList.add(i)
//                            }
//                        }
//                    }
//                    "운영기관"->{//기관
//                        for(i in serviceList){
//                            if(i.service_institution.contains(searchText)){
//                                tempList.add(i)
//                            }
//                        }
//                    }
//                }
//                var adapter =
//                    MyServiceAdapter(
//                        tempList,
//                        tempList.size,
//                        0,
//                        requireContext()
//                    )
//                adapter.itemClickListener = object :
//                    MyServiceAdapter.OnItemClickListener{
//                    override fun OnItemClick(
//                        holder: MyServiceAdapter.MyViewHolder,
//                        view: View,
//                        itemId: String,
//                        position: Int
//                    ) {
//                        val intent = Intent(requireContext(),ServiceInfoActivity::class.java)
//                        intent.putExtra("itemId",itemId)
//                        startActivity(intent)
//                        activity?.overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left)
//                    }
//
//                }
//                recyclerView_service.adapter = adapter
//                progressDialog2.dismiss()
            }
        }

        val arraySpinner = requireContext().resources.getStringArray(R.array.array_searchServiceMenu)
        var spinnerAdapter = ArrayAdapter(requireContext(),R.layout.item_spinner,arraySpinner)
        spinner_search.adapter = spinnerAdapter
    }

    inner class MyAsyncTask: AsyncTask<String, String, String>(){

        lateinit var progressDialog :ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.show()
            progressDialog.setMessage("정보를 불러오는 중입니다..")
        }

        override fun doInBackground(vararg params: String?): String {
            val doc = Jsoup.connect(url1).get()

            //전체 데이터 개수
            val totalCount = doc.select("totalCount")
            println(totalCount.text())
            totalCount1 = totalCount.text().toInt()
            //데이터 전체 doc
            val result_doc = Jsoup.connect(url1+getPageInfo(1,totalCount.text().toInt())).get()

            //각 데이터 item
            val svcIdList = result_doc.select("svc")
            for(item in svcIdList){
                val itemId = item.select("svcId").text()
                val itemName = item.select("svcNm").text()
                val itemInstitution = item.select("jrsdDptAllNm").text()
                serviceList.add(ServiceData(itemId,itemName,itemInstitution))
            }

            val doc2 = Jsoup.connect(url2).get()

            //전체 데이터 개수
            val totalCount_2 = doc2.select("totalCount")
            println(totalCount_2.text())

            totalCount2 = totalCount_2.text().toInt()

            //데이터 전체 doc
            val result_doc2 = Jsoup.connect(url2+getPageInfo(1,totalCount_2.text().toInt())).get()

            //각 데이터 item
            val svcIdList2 = result_doc2.select("svc")
            for(item in svcIdList2){
                val itemId = item.select("svcId").text()
                val itemName = item.select("svcNm").text()
                val itemInstitution = item.select("jrsdDptAllNm").text()
                serviceList.add(ServiceData(itemId,itemName,itemInstitution))
            }

            return doc.title()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()
            recyclerView_service.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,false)
            var adapter =
                MyServiceAdapter(
                    serviceList,
                    totalCount1,
                    totalCount2,
                    requireContext()
                )
            adapter.itemClickListener = object :
                MyServiceAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: MyServiceAdapter.MyViewHolder,
                    view: View,
                    itemId: String,
                    position: Int
                ) {
                    val intent = Intent(requireContext(),ServiceInfoActivity::class.java)
                    intent.putExtra("itemId",itemId)
                    startActivity(intent)
                }

            }
            recyclerView_service.adapter = adapter
        }
    }


    inner class MyAsyncTask2: AsyncTask<String, String, String>(){

        lateinit var progressDialog :ProgressDialog
        var tempList = arrayListOf<ServiceData>()

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.show()
            progressDialog.setMessage("검색 중입니다. 잠시만 기다려주세요...")
        }

        override fun doInBackground(vararg params: String?): String {

            var searchText = searchEdit.text.toString()

            when(spinner_search.selectedItem.toString()){
                "전체"->{//전체
                    for(i in serviceList){
                        if(i.service_name.contains(searchText) || i.service_institution.contains(searchText)){
                            tempList.add(i)
                        }
                    }
                }
                "제목"->{//제목
                    for(i in serviceList){
                        if(i.service_name.contains(searchText)){
                            tempList.add(i)
                        }
                    }
                }
                "운영기관"->{//기관
                    for(i in serviceList){
                        if(i.service_institution.contains(searchText)){
                            tempList.add(i)
                        }
                    }
                }
            }

            return "test"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var adapter =
                MyServiceAdapter(
                    tempList,
                    tempList.size,
                    0,
                    requireContext()
                )
            adapter.itemClickListener = object :
                MyServiceAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: MyServiceAdapter.MyViewHolder,
                    view: View,
                    itemId: String,
                    position: Int
                ) {
                    val intent = Intent(requireContext(),ServiceInfoActivity::class.java)
                    intent.putExtra("itemId",itemId)
                    startActivity(intent)
                    activity?.overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left)
                }

            }
            recyclerView_service.adapter = adapter
            progressDialog.dismiss()
        }
    }
}