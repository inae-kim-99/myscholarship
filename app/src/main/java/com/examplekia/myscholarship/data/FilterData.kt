package com.examplekia.myscholarship.data

import java.io.Serializable

// list size 가 0이면 전체, 0이 아니면 선택 목록 인덱스
class FilterData : Serializable{
    var searchType:String?=null
    var searchText:String? = null
    var isFilter:Boolean=false
    var pd_list = arrayListOf<String>()
    var pinstitutiond_list = arrayListOf<String>()
    var pscholarshipd_list = arrayListOf<String>()
    var puniv_list = arrayListOf<String>()
    var pgrade_list = arrayListOf<String>()


}