package com.examplekia.myscholarship.menu.scholarship

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.FilterData
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterActivity : AppCompatActivity() {

    var filterMap1 = mutableMapOf<String,Boolean>()
    var filterMap2 = mutableMapOf<String,Boolean>()
    var filterMap3 = mutableMapOf<String,Boolean>()
    var filterMap4 = mutableMapOf<String,Boolean>()
    var filterMap5 = mutableMapOf<String,Boolean>()

    lateinit var shape:GradientDrawable
    lateinit var shape2:GradientDrawable

    lateinit var array1:Array<String>//pd
    lateinit var array2:Array<String>//pinstitutiond
    lateinit var array3:Array<String>//pscholarshipd
    lateinit var array4:Array<String>//univ
    lateinit var array5:Array<String>//grade


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        init()
    }

    private fun init() {

        makeFilterBtn()

        closeBtn_filter.setOnClickListener {
            this.finish()
        }

        applyBtn_filter.setOnClickListener {
            var data = checkFilterBtn()
            val intent = Intent()
            intent.putExtra("filterData",data)
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }

        resetBtn_filter.setOnClickListener{
           val intent = intent
            finish()
            startActivity(intent)
        }



    }

    private fun setResetBtn(){
        resetBtn_filter.isEnabled = true
        resetBtn_filter.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
    }

    private fun checkFilterBtn():FilterData {
        var filterData = FilterData()

        if(filterMap1["전체"] == false){
            var tempList = arrayListOf<String>()
            for(str in array1){
                if(filterMap1[str] != null && filterMap1[str] == true){
                    tempList.add(str)
                    println(str)
                }
            }
            filterData.pd_list = tempList
        }

        if(filterMap2["전체"] == false){
            var tempList = arrayListOf<String>()
            for(str in array2){
                if(filterMap2[str] != null && filterMap2[str] == true){
                    tempList.add(str)
                    println(str)
                }
            }
            filterData.pinstitutiond_list = tempList
        }

        if(filterMap3["전체"] == false){
            var tempList = arrayListOf<String>()
            for(str in array3){
                if(filterMap3[str] != null && filterMap3[str] == true){
                    tempList.add(str)
                    println(str)
                }
            }
            filterData.pscholarshipd_list = tempList

        }

        if(filterMap4["전체"] == false){
            var tempList = arrayListOf<String>()
            for(str in array4){
                if(filterMap4[str] != null && filterMap4[str] == true){
                    tempList.add(str)
                    println(str)
                }
            }
            filterData.puniv_list = tempList

        }

        if(filterMap5["전체"] == false){
            var tempList = arrayListOf<String>()
            for(str in array5){
                if(filterMap5[str] != null && filterMap5[str] == true){
                    tempList.add(str)
                    println(str)
                }
            }
            filterData.pgrade_list = tempList
        }

        return filterData
    }

    fun makeShape1():GradientDrawable{
        val shape = GradientDrawable()
        shape.setColor(ContextCompat.getColor(this,R.color.white))
        shape.setStroke(3,ContextCompat.getColor(this,R.color.background_gray))
        shape.cornerRadius = 30f
        return shape
    }

    fun makeShape2():GradientDrawable{
        val shape2 = GradientDrawable()
        shape2.setColor(ContextCompat.getColor(this,R.color.colorPrimary))
        shape2.setStroke(3,ContextCompat.getColor(this,R.color.colorPrimary))
        shape2.cornerRadius = 30f
        return shape2
    }



    private fun makeFilterBtn() {
        //make shape
        shape = GradientDrawable()
        shape.setColor(ContextCompat.getColor(this,R.color.white))
        shape.setStroke(3,ContextCompat.getColor(this,R.color.background_gray))
        shape.cornerRadius = 30f

        shape2 = GradientDrawable()
        shape2.setColor(ContextCompat.getColor(this,R.color.colorPrimary))
        shape2.setStroke(3,ContextCompat.getColor(this,R.color.colorPrimary))
        shape2.cornerRadius = 30f

        //P_D
        val filter1 = findViewById<LinearLayout>(R.id.scholarshipGroup_filter)
        array1 = resources.getStringArray(R.array.array_P_D)

        for(i in 0 .. array1.size){

            val v = LayoutInflater.from(this).inflate(R.layout.item_filter,filter1,false)
            //전체 버튼
            if(i == 0){
                v.textView_filter.text = "전체"
                v.textView_filter.background = makeShape2()
                v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                filterMap1["전체"] = true
                //전체 선택시 다른 버튼 모두 false
                v.setOnClickListener {
                    if(filterMap1["전체"] == false){
                        filterMap1["전체"] = true
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                        for(j in 1 until filter1.childCount){
                            filterMap1[array1[j-1]] = false
                            filter1.getChildAt(j).textView_filter.background =makeShape1()
                            filter1.getChildAt(j).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                    }else{
                        filterMap1["전체"] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }


                }
            }
            //다른 버튼들
            else{
                v.textView_filter.text = array1[i-1]
                v.textView_filter.background = makeShape1()
                filterMap1[array1[i-1]] = false
                v.setOnClickListener {
                    setResetBtn()
                    if(filterMap1[array1[i-1]]!!) {//true이면
                        filterMap1[array1[i-1]] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }
                    else {//false이면
                        filterMap1[array1[i-1]] = true
                        if(filterMap1["전체"] == true){
                            filterMap1["전체"] = false
                            filter1.getChildAt(0).textView_filter.background = makeShape1()
                            filter1.getChildAt(0).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                    }
                }
            }
            filter1.addView(v)
        }

        //PINSTITUTION_D
        val filter2 = findViewById<LinearLayout>(R.id.institutionGroup_filter)
        array2 = resources.getStringArray(R.array.array_PINSTITUTION_D)

        for(i in 0 .. array2.size){

            val v = LayoutInflater.from(this).inflate(R.layout.item_filter,filter2,false)
            //전체 버튼
            if(i == 0){
                v.textView_filter.text = "전체"
                v.textView_filter.background = makeShape2()
                v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                filterMap2["전체"] = true
                //전체 선택시 다른 버튼 모두 false
                v.setOnClickListener {
                    if(filterMap2["전체"] == false){
                        filterMap2["전체"] = true
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                        for(j in 1 until filter2.childCount){
                            filterMap2[array2[j-1]] = false
                            filter2.getChildAt(j).textView_filter.background =makeShape1()
                            filter2.getChildAt(j).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                    }else{
                        filterMap2["전체"] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }


                }
            }
            //다른 버튼들
            else{
                v.textView_filter.text = array2[i-1]
                v.textView_filter.background = makeShape1()
                filterMap2[array2[i-1]] = false
                v.setOnClickListener {
                    setResetBtn()
                    if(filterMap2[array2[i-1]]!!) {//true이면
                        filterMap2[array2[i-1]] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }
                    else {//false이면
                        filterMap2[array2[i-1]] = true
                        if(filterMap2["전체"] == true){
                            filterMap2["전체"] = false
                            filter2.getChildAt(0).textView_filter.background = makeShape1()
                            filter2.getChildAt(0).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                    }
                }
            }
            filter2.addView(v)
        }

        //PSCHOLARSHIP_D
        val filter3 = findViewById<LinearLayout>(R.id.scholarshipdGroup_filter)
        array3 = resources.getStringArray(R.array.array_PSCHOLARSHIP_D)

        for(i in 0 .. array3.size){

            val v = LayoutInflater.from(this).inflate(R.layout.item_filter,filter3,false)
            //전체 버튼
            if(i == 0){
                v.textView_filter.text = "전체"
                v.textView_filter.background = makeShape2()
                v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                filterMap3["전체"] = true
                //전체 선택시 다른 버튼 모두 false
                v.setOnClickListener {
                    if(filterMap3["전체"] == false){
                        filterMap3["전체"] = true
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                        for(j in 1 until filter3.childCount){
                            filterMap3[array3[j-1]] = false
                            filter3.getChildAt(j).textView_filter.background =makeShape1()
                            filter3.getChildAt(j).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                    }else{
                        filterMap3["전체"] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }

                }
            }
            //다른 버튼들
            else{
                v.textView_filter.text = array3[i-1]
                v.textView_filter.background = makeShape1()
                filterMap3[array3[i-1]] = false
                v.setOnClickListener {
                    setResetBtn()
                    if(filterMap3[array3[i-1]]!!) {//true이면
                        filterMap3[array3[i-1]] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }
                    else {//false이면
                        filterMap3[array3[i-1]] = true
                        if(filterMap3["전체"] == true){
                            filterMap3["전체"] = false
                            filter3.getChildAt(0).textView_filter.background = makeShape1()
                            filter3.getChildAt(0).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                    }
                }
            }
            filter3.addView(v)
        }

        //PUNIV_D
        val filter4 = findViewById<LinearLayout>(R.id.univGroup_filter)
        array4= resources.getStringArray(R.array.array_PUNIV_D)

        for(i in 0 .. array4.size){

            val v = LayoutInflater.from(this).inflate(R.layout.item_filter,filter4,false)
            //전체 버튼
            if(i == 0){
                v.textView_filter.text = "전체"
                v.textView_filter.background = makeShape2()
                v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                filterMap4["전체"] = true
                //전체 선택시 다른 버튼 모두 false
                v.setOnClickListener {
                    if(filterMap4["전체"] == false){
                        filterMap4["전체"] = true
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                        for(j in 1 until filter4.childCount){
                            filterMap4[array4[j-1]] = false
                            filter4.getChildAt(j).textView_filter.background =makeShape1()
                            filter4.getChildAt(j).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                    }else{
                        filterMap4["전체"] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }


                }
            }
            //다른 버튼들
            else{
                v.textView_filter.text = array4[i-1]
                v.textView_filter.background = makeShape1()
                filterMap4[array4[i-1]] = false
                v.setOnClickListener {
                    setResetBtn()
                    if(filterMap4[array4[i-1]]!!) {//true이면
                        filterMap4[array4[i-1]] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }
                    else {//false이면
                        filterMap4[array4[i-1]] = true
                        if(filterMap4["전체"] == true){
                            filterMap4["전체"] = false
                            filter4.getChildAt(0).textView_filter.background = makeShape1()
                            filter4.getChildAt(0).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                    }
                }
            }
            filter4.addView(v)
        }

        //PGRADE_D
        val filter5 = findViewById<LinearLayout>(R.id.gradeGroup_filter)
        array5= resources.getStringArray(R.array.array_PGRADE_D)

        for(i in 0 .. array5.size){

            val v = LayoutInflater.from(this).inflate(R.layout.item_filter,filter5,false)
            //전체 버튼
            if(i == 0){
                v.textView_filter.text = "전체"
                v.textView_filter.background = makeShape2()
                v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                filterMap5["전체"] = true
                //전체 선택시 다른 버튼 모두 false
                v.setOnClickListener {
                    println("hihi")
                    if(filterMap5["전체"] == false){
                        filterMap5["전체"] = true
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                        for(j in 1 until filter5.childCount){
                            filterMap5[array5[j-1]] = false
                            filter5.getChildAt(j).textView_filter.background =makeShape1()
                            filter5.getChildAt(j).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                    }else{
                        filterMap5["전체"] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }


                }
            }
            //다른 버튼들
            else{
                v.textView_filter.text = array5[i-1]
                v.textView_filter.background = makeShape1()
                filterMap5[array5[i-1]] = false
                v.setOnClickListener {
                    setResetBtn()
                    if(filterMap5[array5[i-1]]!!) {//true이면
                        filterMap5[array5[i-1]] = false
                        v.textView_filter.background = makeShape1()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                    }
                    else {//false이면
                        filterMap5[array5[i-1]] = true
                        if(filterMap5["전체"] == true){
                            filterMap5["전체"] = false
                            filter5.getChildAt(0).textView_filter.background = makeShape1()
                            filter5.getChildAt(0).textView_filter.setTextColor(ContextCompat.getColor(this,R.color.black))
                        }
                        v.textView_filter.background = makeShape2()
                        v.textView_filter.setTextColor(ContextCompat.getColor(this,R.color.white))
                    }
                }
            }
            filter5.addView(v)
        }
    }
}
