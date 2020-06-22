package com.examplekia.myscholarship.menu.support

import android.graphics.Typeface
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.examplekia.myscholarship.MyDBHelper
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.ServiceData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_info.linearLayout
import kotlinx.android.synthetic.main.activity_service_info.*
import org.jsoup.Jsoup

class ServiceInfoActivity : AppCompatActivity() {

    lateinit var myDBHelper: MyDBHelper
    var svcId = ""
    var svcNm =""
    var jrsdDptAllNm=""
    var url = "http://api.korea.go.kr/openapi/svc?serviceKey=P5pThUxFlhq9WgbVUacTrkmFQEQLzEOKjyHIzVLgAPHRuc79fx7W1abgUdFWgzZQXhJG7EIPCVveFHxNyW3bEA%3D%3D&format=xml&svcId="
    lateinit var toast: Toast


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_info)
        init()
    }

    private fun init() {
        toast = Toast(this)
        myDBHelper = MyDBHelper(this)

        val intent = intent
        svcId = intent.getStringExtra("itemId")!!
        url = url + svcId
        MyAsyncTask().execute(url)

        closeBtn_service.setOnClickListener {
            this.finish()
            overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right)
        }


        val user = FirebaseAuth.getInstance().currentUser!!
        val rdb = FirebaseDatabase.getInstance().getReference("Users")
            .child(user.uid).child("favoriteServiceList")
        rdb.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(i in p0.children){
                    val id = i.child("svcId").getValue().toString()
                    if(svcId == id){
                        isFavorite_service.isChecked = true
                    }

                }

            }
        })

        //즐겨찾기 버튼 클릭
        isFavorite_service.setOnClickListener {
            if(isFavorite_service.isChecked){
                //val result = myDBHelper.addFavorite(index)
                val user = FirebaseAuth.getInstance().currentUser
                if(user!=null){
                    val rdb = FirebaseDatabase.getInstance().getReference("Users")
                    var childUpdates= mutableMapOf<String,Any>()
                    childUpdates.put(svcId,ServiceData(svcId,svcNm,jrsdDptAllNm))
                    rdb.child(user.uid).child("favoriteServiceList").updateChildren(childUpdates)
                    toast.cancel()
                    toast = Toast.makeText(this,"즐겨찾기로 등록합니다.",Toast.LENGTH_SHORT)
                    toast.show()

                }
            }else{
                val user = FirebaseAuth.getInstance().currentUser
                if(user != null){
                    val rdb = FirebaseDatabase.getInstance().getReference("Users")
                        .child(user.uid).child("favoriteServiceList")
                    rdb.child(svcId).removeValue()
                    toast.cancel()
                    toast = Toast.makeText(this,"즐겨찾기를 해제합니다.",Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    inner class MyAsyncTask: AsyncTask<String, String, String>(){

        var svcPpo:String?=null
        var sportFr:String?=null
        var sportTg:String?=null
        var reqstProcessPd:String?=null
        var svcCts:String?=null//지원내용
        var slctnStdr:String?=null//선정기준
        var duplnprtySvc:String?=null//중복수혜불가서비스
        var reqstProcss:String?=null//신청절차
        var posesPapers:String?=null//구비서류
        var onlnReqstSiteUrl:String?=null//사이트주소
        var rcvOrgNm:String?=null//접수기관명
        var rcvOrgTelNo:String?=null//접수기관 전화번호
        var refrncNm:String?=null//문의처 명
        var refrncTelNo:String?=null//문의처 전화번호
        var refrncSiteUrl:String?=null//문의처 사이트주소

        override fun doInBackground(vararg params: String?): String {
            val doc = Jsoup.connect(url).get()

            //요청 결과
            val resultCode = doc.select("resultCode")
            if(resultCode.text() == "00"){
                val item = doc.select("svc")

                //타이틀
                svcNm = item.select("svcNm").text()
                jrsdDptAllNm = item.select("jrsdDptAllNm").text()

                //주요 설명
                svcPpo = item.select("svcPpo").text()
                sportFr = item.select("sportFr").text()
                sportTg = item.select("sportTg").text()
                reqstProcessPd = item.select("reqstProcessPd").text()

                //세부 설명
                svcCts = item.select("svcCts").text()
                slctnStdr = item.select("slctnStdr").text()
                duplnprtySvc = item.select("duplnprtySvc").text()
                reqstProcss = item.select("reqstProcss").text()
                posesPapers = item.select("posesPapers").text()
                onlnReqstSiteUrl = item.select("onlnReqstSiteUrl").text()
                rcvOrgNm = item.select("rcvOrgNm").text()
                rcvOrgTelNo = item.select("rcvOrgTelNo").text()
                refrncNm = item.select("refrncNm").text()
                refrncTelNo = item.select("refrncTelNo").text()
                refrncSiteUrl = item.select("refrncSiteUrl").text()

            }


            return doc.title()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            //타이틀
            service_name.text = svcNm
            service_institution.text = jrsdDptAllNm

            //주요설명
            if(svcPpo != null && svcPpo !="")
                service_desti.text = svcPpo
            else
                service_desti.text = "사이트 참조"

            if(sportFr != null && sportFr !="")
                service_supportType.text = sportFr
            else
                service_supportType.text = "사이트 참조"

            if(sportTg != null && sportTg !="")
                service_person.text = sportTg
            else
                service_person.text = "사이트 참조"

            if(reqstProcessPd != "" && reqstProcessPd != null)
                service_date.text = reqstProcessPd
            else
                service_date.text = "사이트 참조"


            //세부설명
            makeTitleView("지원 내용")
            makeContentView(svcCts)

            makeTitleView("선정 기준")
            makeContentView(slctnStdr )

            makeTitleView("중복 수혜 불가 서비스")
            makeContentView(duplnprtySvc )

            makeTitleView("신청 절차")
            makeContentView(reqstProcss )

            makeTitleView("구비 서류")
            makeContentView(posesPapers )

            makeTitleView("사이트 주소")
            makeLink(onlnReqstSiteUrl )

            makeTitleView("접수기관명")
            makeContentView(rcvOrgNm )

            makeTitleView("접수기관 전화번호")
            makeContentView(rcvOrgTelNo )

            makeTitleView("문의처 명")
            makeContentView(refrncNm )

            makeTitleView("문의처 전화번호")
            makeContentView(refrncTelNo  )

            makeTitleView("문의처 사이트")
            makeLink(refrncSiteUrl  )
        }
    }

    fun makeTitleView(str:String?){
        if(str != null){
            var title = TextView(this)
            title.textSize = 14f
            title.setTextColor(resources.getColor(R.color.black))
            title.typeface = Typeface.DEFAULT_BOLD
            title.setPadding(10)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(10)
            title.layoutParams = params
            title.text = "* "+str
            linearLayout.addView(title)
        }
    }

    fun makeContentView(str:String?){
        if(str != null){
            var content = TextView(this)
            content.textSize = 14f
            //content.setTextColor(resources.getColor(R.color.m))
            content.setPadding(10)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(10,10,10,40)
            if(str =="")
                content.text = "정보 없음"
            else
                content.text = str
            content.layoutParams = params
            linearLayout.addView(content)
        }
    }

    fun makeLink(str:String?){
        if(str != null){
            var content = TextView(this)
            content.textSize = 14f
            //content.setTextColor(resources.getColor(R.color.m))
            content.setPadding(10)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(10,10,10,40)

            if(str =="")
                content.text = "정보 없음"
            else{
                content.linksClickable = true
                content.movementMethod=LinkMovementMethod.getInstance()

                var resultStr = "<a href=\'"+str+"\'>"+str+"</a><br>"
                content.setText(Html.fromHtml(resultStr))
            }

            content.layoutParams = params
            linearLayout.addView(content)
        }
    }

}
