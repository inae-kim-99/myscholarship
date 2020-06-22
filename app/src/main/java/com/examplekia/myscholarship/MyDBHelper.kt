package com.examplekia.myscholarship

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import com.examplekia.myscholarship.data.FilterData
import com.examplekia.myscholarship.data.MyData
import com.examplekia.myscholarship.data.ServiceData
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception

class MyDBHelper(
    val context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        val DB_VERSION = 1
        val DB_NAME = "mydb.db"
        val TABLE_NAME = "scholarships"
        val TABLE_NAME2 = "favorites"
        val TABLE_NAME_SERVICE = "favoriteservices"
        val PID = "pid"
        val PNUM = "pnum"//번호
        val PINSTITUTION = "pinstitution"//운영기관명
        val PNAME = "pname"//상품명
        val PINSTITUTION_D = "pinstitutiond"//운영기관구분
        val P_D = "pd"//상품구분
        val PSCHOLARSHIP_D="pscholarshipd"//학자금유형구분
        val PQUESTION = "pquestion"//문의처
        val PUNIV_D = "punivd"//대학구분
        val PGRADE_D = "pgraded"//학년구분
        val PDEPART_D = "pdepartd"//학과구분
        val PSCORE = "pscore"//성적기준
        val PINCOME = "pincome"//소득기준
        val PSUPPORT = "psupport"//지원금액
        val PQUALIFI = "pqualifi"//특정자격
        val PAREA = "parea"//지역거주여부
        val PAPPLY_DATE = "papplydate"//신청기간
        val P_SELECT_WAY = "pselectway"//선발방법
        val P_SELECT_NUM = "pselectnum"//선발인원
        val PQUALIFI_LIMIT = "pqualifilimit"//자격제한
        val PRECOMMEND = "precommend"//추천필요여부
        val PDOCUMENT = "pdocument"//제출서류
        val FAVORITE_NUM = "fnum"

        val FAVORITE_ID = "svcId"
        val FAVORITE_NAME="svcNm"
        val FAVORITE_INSTITUTION="jrsDptAllNm"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists "+ TABLE_NAME+"("+
                PID+" integer primary key autoincrement, "+
                PNUM+" text, "+
                PINSTITUTION+" text, "+
                PNAME+" text, "+
                PINSTITUTION_D+" text, "+
                P_D+" text, "+
                PSCHOLARSHIP_D+" text, "+
                PQUESTION+" text, "+
                PUNIV_D+" text, "+
                PGRADE_D+" text, "+
                PDEPART_D+" text, "+
                PSCORE+" text, "+
                PINCOME+" text, "+
                PSUPPORT+" text, "+
                PQUALIFI+" text, "+
                PAREA+" text, "+
                PAPPLY_DATE+" text, "+
                P_SELECT_WAY+" text, "+
                P_SELECT_NUM+" text, "+
                PQUALIFI_LIMIT+" text, "+
                PRECOMMEND+" text, "+
                PDOCUMENT+" text)"
        val create_table_favorite = "create table if not exists "+ TABLE_NAME2+"("+
                PID+" integer primary key autoincrement, "+
                FAVORITE_NUM+" text)"
        val create_table_favorite_service =  "create table if not exists "+ TABLE_NAME_SERVICE+"("+
                PID+" integer primary key autoincrement, "+
                FAVORITE_ID+" text, "+
                FAVORITE_NAME+" text, "+
                FAVORITE_INSTITUTION+" text)"
        db?.execSQL(create_table)
        db?.execSQL(create_table_favorite)
        db?.execSQL(create_table_favorite_service)
    }

    fun testDB():ArrayList<ArrayList<String>>{
        var testList = ArrayList<ArrayList<String>>()
        for(i in 0 until 20)
            testList.add(ArrayList<String>())
        var inputStream = this.context.resources.openRawResource(R.raw.data)
        var inputStreamReader = InputStreamReader(inputStream)
        var reader = BufferedReader(inputStreamReader)

        var xmlPullParserFactory:XmlPullParserFactory?=null
        var xmlPullParser:XmlPullParser?= null

        try{
            xmlPullParserFactory = XmlPullParserFactory.newInstance()
            xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(reader)

            var eventType = xmlPullParser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT){
                when(eventType){
                    XmlPullParser.START_TAG->{
                        if(xmlPullParser.name.equals("번호")){

                            var values = ContentValues()
                            //put 번호
                            eventType = xmlPullParser.next()
                            values.put(PNUM,xmlPullParser.text)

                            //하나의 아이템 생성
                            while(true){
                                eventType = xmlPullParser.next()

                                //하나의 아이템에서 마지막 정보일때
                                if(eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals("제출서류"))
                                    break

                                //text 이고 하나의 아이템의 첫 정보인 "번호"를 가리킬때
                                if(eventType == XmlPullParser.START_TAG) {
                                    if (xmlPullParser.name.equals("운영기관명")) {
                                        eventType = xmlPullParser.next()
                                        testList[0].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("상품명")) {
                                        eventType = xmlPullParser.next()
                                        testList[1].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("운영기관구분")) {
                                        eventType = xmlPullParser.next()
                                        testList[2].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("상품구분")) {
                                        eventType = xmlPullParser.next()
                                        testList[3].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("학자금유형구분")) {
                                        eventType = xmlPullParser.next()
                                        testList[4].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("문의처")) {
                                        eventType = xmlPullParser.next()
                                        testList[5].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("대학구분")) {
                                        eventType = xmlPullParser.next()
                                        testList[6].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("학년구분")) {
                                        eventType = xmlPullParser.next()
                                        testList[7].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("학과구분")) {
                                        eventType = xmlPullParser.next()
                                        testList[8].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("성적기준")) {
                                        eventType = xmlPullParser.next()
                                        testList[9].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("소득기준")) {
                                        eventType = xmlPullParser.next()
                                        testList[10].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("지원금액")) {
                                        eventType = xmlPullParser.next()
                                        testList[11].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("특정자격")) {
                                        eventType = xmlPullParser.next()
                                        testList[12].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("지역거주여부")) {
                                        eventType = xmlPullParser.next()
                                        testList[13].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("신청기간")) {
                                        eventType = xmlPullParser.next()
                                        testList[14].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("선발방법")) {
                                        eventType = xmlPullParser.next()
                                        testList[15].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("선발인원")) {
                                        eventType = xmlPullParser.next()
                                        testList[16].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("자격제한")) {
                                        eventType = xmlPullParser.next()
                                        testList[17].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("추천필요여부")) {
                                        eventType = xmlPullParser.next()
                                        testList[18].add(xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("제출서류")) {
                                        eventType = xmlPullParser.next()
                                        testList[19].add(xmlPullParser.text)
                                    }
                                }
                            }
                        }
                    }
                }
                try{
                    eventType = xmlPullParser.next()
                }catch (e:IOException){
                    e.printStackTrace()
                }
            }
        }catch (e:XmlPullParserException){
            e.printStackTrace()
        }finally {
            try {
                if(reader != null)
                    reader.close()
                if(inputStreamReader != null)
                    inputStreamReader.close()
                if(inputStream != null)
                    inputStream.close()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        return testList
    }

    fun setDB(){
        var inputStream = this.context.resources.openRawResource(R.raw.data)
        var inputStreamReader = InputStreamReader(inputStream)
        var reader = BufferedReader(inputStreamReader)

        var xmlPullParserFactory:XmlPullParserFactory?=null
        var xmlPullParser:XmlPullParser?= null

        try{
            xmlPullParserFactory = XmlPullParserFactory.newInstance()
            xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(reader)

            var eventType = xmlPullParser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT){
                when(eventType){
                    XmlPullParser.START_TAG->{
                        if(xmlPullParser.name.equals("번호")){

                            var values = ContentValues()
                            //put 번호
                            eventType = xmlPullParser.next()
                            values.put(PNUM,xmlPullParser.text)

                            //하나의 아이템 생성
                            while(true){
                                eventType = xmlPullParser.next()

                                //하나의 아이템에서 마지막 정보일때
                                if(eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals("Row"))
                                    break

                                //text 이고 하나의 아이템의 첫 정보인 "번호"를 가리킬때
                                if(eventType == XmlPullParser.START_TAG) {
                                    if (xmlPullParser.name.equals("운영기관명")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PINSTITUTION, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("상품명")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PNAME, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("운영기관구분")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PINSTITUTION_D, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("상품구분")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(P_D, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("학자금유형구분")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PSCHOLARSHIP_D, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("문의처")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PQUESTION, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("대학구분")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PUNIV_D, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("학년구분")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PGRADE_D, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("학과구분")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PDEPART_D, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("성적기준")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PSCORE, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("소득기준")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PINCOME, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("지원금액")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PSUPPORT, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("특정자격")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT){
                                            values.put(PQUALIFI, xmlPullParser.text)
                                        }
                                    } else if (xmlPullParser.name.equals("지역거주여부")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PAREA, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("신청기간")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PAPPLY_DATE, setApplyDate(xmlPullParser.text))
                                    } else if (xmlPullParser.name.equals("선발방법")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(P_SELECT_WAY, xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("선발인원")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(P_SELECT_NUM , xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("자격제한")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PQUALIFI_LIMIT , xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("추천필요여부")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PRECOMMEND , xmlPullParser.text)
                                    } else if (xmlPullParser.name.equals("제출서류")) {
                                        eventType = xmlPullParser.next()
                                        if (eventType == XmlPullParser.TEXT)
                                            values.put(PDOCUMENT , xmlPullParser.text)
                                    }
                                }
                            }
                            val db = this.writableDatabase
                            if(db.insert(TABLE_NAME,null,values)>0)
                                db.close()
                        }
                    }
                }
                try{
                    eventType = xmlPullParser.next()
                }catch (e:IOException){
                    e.printStackTrace()
                }
            }
        }catch (e:XmlPullParserException){
            e.printStackTrace()
        }finally {
            try {
                if(reader != null)
                    reader.close()
                if(inputStreamReader != null)
                    inputStreamReader.close()
                if(inputStream != null)
                    inputStream.close()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }




    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exist "+ TABLE_NAME
        db?.execSQL(drop_table)
        onCreate(db)
    }

    fun getAllRecord(){
        val strsql = "select * from " + TABLE_NAME
        val db = this.readableDatabase
        val cursor =db.rawQuery(strsql,null)
        if(cursor.count != 0){
              showRecord(cursor)
        }
        cursor.close()
        db.close()
    }

    fun setApplyDate(str:String):String{
        if(str.substring(0,2) == "- "){
            return str.substring(2,str.length)
        }else if(str[0] == '-'){
            return str.substring(1,str.length)
        }
        return str
    }

    fun searchItems(filterData: FilterData):ArrayList<String>?{
        val pnumList = arrayListOf<String>()
        val resultList = arrayListOf<String>()

        //검색어가 포함되었는지
        if(filterData.searchText == null){
            if(filterData.isFilter){
                for(i in 1 .. getRecordCount()){
                    if(checkItem(i.toString(),filterData))
                        resultList.add(i.toString())
                }
                return resultList
            }else{
                return null
            }
        }
        else{ //검색어가 있으면
            var search = filterData.searchText
            val type = this.context.resources.getStringArray(R.array.array_searchMenu)
            var strsql =""
            when(filterData.searchType){
                type[0]->{//전체
                    strsql = "select * from "+ TABLE_NAME+" where "+ PNAME+" like \'%"+search+"%\' or " + PINSTITUTION+" like \'%"+search+"%\'"
                }
                type[1]->{//장학금명
                    strsql = "select * from "+ TABLE_NAME+" where "+ PNAME+" like \'%"+search+"%\'"

                }
                type[2]->{//운영기관
                    strsql = "select * from "+ TABLE_NAME+" where "+ PINSTITUTION+" like \'%"+search+"%\'"
                }
            }
            val db = this.readableDatabase
            val cursor = db.rawQuery(strsql,null)
            if(cursor.count != 0){
                cursor.moveToFirst()
                do{
                    pnumList.add(cursor.getString(cursor.getColumnIndex(PNUM)))
                }while(cursor.moveToNext())
            }

            if(filterData.isFilter){
                for(i in pnumList){
                    if(checkItem(i,filterData))
                        resultList.add(i)
                }
                return resultList
            }else{
                return pnumList
            }
        }


    }

    fun checkItem(pnum: String,filterData: FilterData):Boolean{
        val data = showItem(pnum)
        var isCheck = false

        if(data != null){

            if(filterData.pd_list.size > 0){
                var check = false
                for(p in filterData.pd_list){
                    if(data.P_D.contains(p)){
                        check = true
                    }
                }
                if(!check)
                    return false
            }

            if(filterData.pinstitutiond_list.size > 0){
                var check = false
                for(p in filterData.pinstitutiond_list){
                    if(data.PINSTITUTION_D.contains(p)){
                        check = true
                    }
                }
                if(!check)
                    return false
            }

            if(filterData.pscholarshipd_list.size > 0){
                var check = false
                for(p in filterData.pscholarshipd_list){
                    if(data.PSCHOLARSHIP_D.contains(p)){
                        check = true
                    }
                }
                if(!check)
                    return false
            }

            if(filterData.puniv_list.size > 0){
                var check = false
                for(p in filterData.puniv_list){
                    if(data.PUNIV_D.contains(p)){
                        check = true
                    }
                }
                if(!check)
                    return false
            }

            if(filterData.pgrade_list.size > 0){
                var check = false
                for(p in filterData.pgrade_list){
                    if(data.PGRADE_D.contains(p)){
                        check = true
                    }
                }
                if(!check)
                    return false
            }

            return true
        }
        else{
            return false
        }
    }

    fun showItem(pnum:String): MyData?{
        val strsql = "select * from "+ TABLE_NAME+" where "+ PNUM+" = \'"+pnum+"\' limit 22"
        val db = this.readableDatabase
        val cursor = db.rawQuery(strsql,null)
        var infoList = arrayListOf<String>()

        if(cursor.count != 0){
            cursor.moveToFirst()
            val count = cursor.columnCount
            for(i in 0 until count){ // 0 ~ 22
                if(cursor.getString(i) != null){
                    infoList.add(cursor.getString(i))
                }else{
                    infoList.add("")
                }
            }
            cursor.close()
            db.close()
            val data = MyData(infoList)
            return data
        }
        return null
    }

    fun showRecord(cursor: Cursor){
        cursor.moveToFirst()
        val count = cursor.columnCount
        val recordCount = cursor.count
        val activity = context as MainActivity
        //activity.tableLayout.removeAllViewsInLayout()

        //컬럼 타이틀 만들기
        val tableRow = TableRow(activity)
        val rowParam = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,3f)
        tableRow.layoutParams = rowParam
        val viewParam = TableRow.LayoutParams(0,100,1f)
        for(i in 0 until 3){
            val textView = TextView(activity)
            textView.layoutParams = viewParam
            textView.text = cursor.getColumnName(i)
            textView.setBackgroundColor(Color.LTGRAY)
            textView.textSize = 15.0f
            textView.gravity = Gravity.CENTER
            tableRow.addView(textView)
        }
        //activity.tableLayout.addView(tableRow)

        //레코드 읽어오기
        do{
            val row = TableRow(activity)
            row.layoutParams = rowParam
//            row.setOnClickListener {
//                for(i in 0 until count){
//                    val txtView = row.getChildAt(i) as TextView
//                    when(txtView.tag){
//                        0-> activity.pIdEdit.setText(txtView.text)
//                        1-> activity.pNameEdit.setText(txtView.text)
//                        2-> activity.pQuantityEdit.setText(txtView.text)
//                    }
//                }
//            }
            for(i in 0 until 3){
                val textView = TextView(activity)
                textView.layoutParams = viewParam
                textView.text = cursor.getString(i)
                textView.textSize = 13.0f
                textView.setTag(i)
                row.addView(textView)
            }
            //activity.tableLayout.addView(row)
        }while (cursor.moveToNext())
    }

    fun getRecordCount():Int{
        val strsql = "select * from " + TABLE_NAME
        val db = this.readableDatabase
        val cursor =db.rawQuery(strsql,null)
        return cursor.count
    }

    fun addFavorite(pnum: String):Boolean{
        val values = ContentValues()
        values.put(FAVORITE_NUM,pnum)
        val db = this.writableDatabase
        if(db.insert(TABLE_NAME2, null, values) > 0){
            db.close()
            return true
        }else {
            db.close()
            return false
        }
    }

    fun deleteFavorite(pnum: String):Boolean{
        val strsql = "select * from " + TABLE_NAME2+" where "+ FAVORITE_NUM+" = \'"+pnum+"\'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(strsql,null)
        if(cursor.moveToFirst()){
            db.delete(TABLE_NAME2, FAVORITE_NUM+"=?", arrayOf(pnum))
            cursor.close()
            db.close()
            return true
        }
        cursor.close()
        db.close()
        return false
    }

    fun getFavoriteList():ArrayList<String>{
        var pnumList = arrayListOf<String>()

        val strsql = "select * from " + TABLE_NAME2
        val db = this.readableDatabase
        val cursor =db.rawQuery(strsql,null)
        if(cursor.count != 0){
            cursor.moveToFirst()
            do{
                val pnum = cursor.getString(cursor.getColumnIndex(FAVORITE_NUM))
                pnumList.add(pnum)
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return pnumList
    }

    fun addFavoriteService(item:ServiceData):Boolean{
        val values = ContentValues()
        values.put(FAVORITE_ID,item.svcId)
        values.put(FAVORITE_NAME,item.service_name)
        values.put(FAVORITE_INSTITUTION,item.service_institution)
        val db = this.writableDatabase
        if(db.insert(TABLE_NAME_SERVICE, null, values) > 0){
            db.close()
            return true
        }else {
            db.close()
            return false
        }
    }

    fun deleteFavoriteService(itemId: String):Boolean{
        val strsql = "select * from " + TABLE_NAME2+" where "+ FAVORITE_ID+" = \'"+itemId+"\'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(strsql,null)
        if(cursor.moveToFirst()){
            db.delete(TABLE_NAME_SERVICE, FAVORITE_ID+"=?", arrayOf(itemId))
            cursor.close()
            db.close()
            return true
        }
        cursor.close()
        db.close()
        return false
    }

    fun getFavoriteServiceList():ArrayList<ServiceData>{
        var serviceDataList = arrayListOf<ServiceData>()

        val strsql = "select * from " + TABLE_NAME_SERVICE
        val db = this.readableDatabase
        val cursor =db.rawQuery(strsql,null)
        if(cursor.count != 0){
            cursor.moveToFirst()
            do{
                val id = cursor.getString(cursor.getColumnIndex(FAVORITE_ID))
                val name = cursor.getString(cursor.getColumnIndex(FAVORITE_NAME))
                val institution = cursor.getString(cursor.getColumnIndex(FAVORITE_INSTITUTION))
                serviceDataList.add(ServiceData(id,name,institution))
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return serviceDataList
    }
}

//    fun intsertProduct(product:Product):Boolean{
//        val values = ContentValues()
//        values.put(PNAME,product.pName)
//        values.put(PQUANTITY,product.pQuantity)
//        val db = this.writableDatabase
//        if(db.insert(TABLE_NAME, null, values) > 0){
//            val activity = context as MainActivity
//            activity.pIdEdit.setText("")
//            activity.pNameEdit.setText("")
//            activity.pQuantityEdit.setText("")
//            db.close()
//            return true
//        }else {
//            db.close()
//            return false
//        }
//    }
//
//    fun updateProduct(product: Product):Boolean{
//        val strsql = "select * from "+ TABLE_NAME+" where "+ PID+" = \'"+product.pId+"\'"
//        val db = this.writableDatabase
//        val cursor = db.rawQuery(strsql,null)
//        if(cursor.moveToFirst()){
//            val values = ContentValues()
//            values.put(PNAME,product.pName)
//            values.put(PQUANTITY,product.pQuantity)
//            db.update(TABLE_NAME, values, PID+"=?", arrayOf(product.pId.toString()))
//            val activity = context as MainActivity
//            activity.pIdEdit.setText("")
//            activity.pNameEdit.setText("")
//            activity.pQuantityEdit.setText("")
//            cursor.close()
//            db.close()
//            return true
//        }
//        cursor.close()
//        db.close()
//        return false
//    }
//
//    fun deleteProduct(pid:String):Boolean{
//        val strsql = "select * from "+ TABLE_NAME+" where "+ PID+" = \'"+pid+"\'"
//        val db = this.writableDatabase
//        val cursor = db.rawQuery(strsql,null)
//        if(cursor.moveToFirst()){
//            db.delete(TABLE_NAME, PID+"=?", arrayOf(pid))
//            val activity = context as MainActivity
//            activity.pIdEdit.setText("")
//            activity.pNameEdit.setText("")
//            activity.pQuantityEdit.setText("")
//            cursor.close()
//            db.close()
//            return true
//        }
//        cursor.close()
//        db.close()
//        return false
//    }
//
//    // select * from produts where pname = '새우깡'
//    fun findProduct(pname:String):Boolean{
//        val strsql = "select * from "+ TABLE_NAME+" where "+ PNAME+" = \'"+pname+"\'"
//        val db = this.readableDatabase
//        val cursor = db.rawQuery(strsql,null)
//        if(cursor.count != 0){
//            showRecord(cursor)
//            cursor.close()
//            db.close()
//            return true
//        }
//        cursor.close()
//        db.close()
//        return false
//    }
//
//    // select * from produts where pname like '김%'
//    fun findProduct2(pname:String):Boolean{
//        val strsql = "select * from "+ TABLE_NAME+" where "+ PNAME+" like \'"+pname+"%\'"
//        val db = this.readableDatabase
//        val cursor = db.rawQuery(strsql,null)
//        if(cursor.count != 0){
//            showRecord(cursor)
//            cursor.close()
//            db.close()
//            return true
//        }
//        cursor.close()
//        db.close()
//        return false
//    }