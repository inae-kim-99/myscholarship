package com.examplekia.myscholarship.menu.scholarship

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.examplekia.myscholarship.MyDBHelper
import com.examplekia.myscholarship.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    var index=""
    lateinit var myDBHelper: MyDBHelper
    lateinit var toast:Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        init()
    }

    private fun init() {
        toast = Toast(this)

        myDBHelper = MyDBHelper(this)

        val intent = getIntent()
        index = intent.getStringExtra("position") as String
        if(index != ""){
            var temp1 =""
            var temp2= ""
            if(!index.contains(",") && index.length > 3){
                temp1 =index.substring(0,index.length-3)
                temp2 = index.substring(index.length-3,index.length)
                index = temp1+","+temp2
            }
            val user = FirebaseAuth.getInstance().currentUser!!
            val rdb = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.uid).child("favoriteScholarList")
            rdb.addValueEventListener(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(i in p0.children){
                        if(index == i.getValue().toString()){
                            isFavorite.isChecked = true

                        }

                    }

                }
            })

            setContent()
            initBtn()
        }else{
            println("index -1")
            initBtn()
        }

    }

    private fun initBtn() {
        //이전 버튼 클릭
        closeBtn_filter.setOnClickListener {
            this.finish()
            overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right)
        }

        //즐겨찾기 버튼 클릭
        isFavorite.setOnClickListener {
            if(isFavorite.isChecked){
                //val result = myDBHelper.addFavorite(index)
                val user = FirebaseAuth.getInstance().currentUser
                if(user!=null){
                    val rdb = FirebaseDatabase.getInstance().getReference("Users")
                    var childUpdates= mutableMapOf<String,Any>()
                    childUpdates.put(index,index)
                    rdb.child(user.uid).child("favoriteScholarList").updateChildren(childUpdates)
                        toast.cancel()
                        toast = Toast.makeText(this,"즐겨찾기로 등록합니다.",Toast.LENGTH_SHORT)
                        toast.show()
                }
            }else{
                val user = FirebaseAuth.getInstance().currentUser
                if(user != null){
                    val rdb = FirebaseDatabase.getInstance().getReference("Users")
                        .child(user.uid).child("favoriteScholarList")
                    rdb.child(index).removeValue()
                    toast.cancel()
                    toast = Toast.makeText(this,"즐겨찾기를 해제합니다.",Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    private fun setContent() {

        val data = myDBHelper.showItem(index)

        if(data != null){
            data.showMyData()
            //타이틀 내용
            scholarship_name.text = data.PNAME
            scholarship_institution.text =data.PINSTITUTION

            //서브타이틀 내용
            scholarship_applydate.text = data.PAPPLY_DATE
            scholarship_d.text = data.PSCHOLARSHIP_D
            scholarship_qualifi.text = data.PQUALIFI
            scholarship_selectnum.text = data.P_SELECT_NUM
            scholarship_support.text = data.PSUPPORT

            //세부 내용
            if(data.PINSTITUTION_D != ""){
                makeTitleView("운영기관구분")
                makeContentView(data.PINSTITUTION_D)
            }

            if(data.P_D != ""){
                makeTitleView("상품 구분")
                makeContentView(data.P_D)
            }

            if(data.PUNIV_D != ""){
                makeTitleView("대학구분")
                makeContentView(data.PUNIV_D)
            }

            if(data.PGRADE_D != ""){
                makeTitleView("학년구분")
                makeContentView(data.PGRADE_D)
            }

            if(data.PDEPART_D != ""){
                makeTitleView("학과구분")
                makeContentView(data.PDEPART_D)
            }

            if(data.PSCORE != ""){
                makeTitleView("성적기준")
                makeContentView(data.PSCORE)
            }

            if(data.PINCOME != ""){
                makeTitleView("소득기준")
                makeContentView(data.PINCOME)
            }

            if(data.PAREA != ""){
                makeTitleView("지역거주여부")
                makeContentView(data.PAREA)
            }

            if(data.P_SELECT_WAY != ""){
                makeTitleView("선발방법")
                makeContentView(data.P_SELECT_WAY)
            }

            if(data.PQUALIFI != ""){
                makeTitleView("자격제한")
                makeContentView(data.PQUALIFI)
            }

            if(data.PDEPART_D != ""){
                makeTitleView("추천필요여부")
                makeContentView(data.PDEPART_D)
            }

            if(data.PDOCUMENT != ""){
                makeTitleView("제출서류")
                makeContentView(data.PDOCUMENT)
            }
        }

    }

    fun makeTitleView(str:String){
        var title = TextView(this)
        title.textSize = 14f
        title.setTextColor(resources.getColor(R.color.black))
        title.typeface = Typeface.DEFAULT_BOLD
        title.setPadding(10)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(10)
        title.layoutParams = params
        title.text = "* "+str
        linearLayout.addView(title)
    }

    fun makeContentView(str:String){
        var content = TextView(this)
        content.textSize = 14f
        //content.setTextColor(resources.getColor(R.color.m))
        content.setPadding(10)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(10,10,10,40)
        content.text = str
        content.layoutParams = params
        linearLayout.addView(content)
    }
}
