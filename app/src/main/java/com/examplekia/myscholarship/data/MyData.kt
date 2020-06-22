package com.examplekia.myscholarship.data

class MyData {
    var PID = "pid"
    var PNUM = "pnum"//번호
    var PINSTITUTION = "pinstitution"//운영기관명
    var PNAME = "pname"//상품명
    var PINSTITUTION_D = "pinstitutiond"//운영기관구분
    var P_D = "pd"//상품구분
    var PSCHOLARSHIP_D="pscholarshipd"//학자금유형구분
    var PQUESTION = "pquestion"//문의처
    var PUNIV_D = "punivd"//대학구분
    var PGRADE_D = "pgraded"//학년구분
    var PDEPART_D = "pdepartd"//학과구분
    var PSCORE = "pscore"//성적기준
    var PINCOME = "pincome"//소득기준
    var PSUPPORT = "psupport"//지원금액
    var PQUALIFI = "pqualifi"//특정자격
    var PAREA = "parea"//지역거주여부
    var PAPPLY_DATE = "papplydate"//신청기간
    var P_SELECT_WAY = "pselectway"//선발방법
    var P_SELECT_NUM = "pselectnum"//선발인원
    var PQUALIFI_LIMIT = "pqualifilimit"//자격제한
    var PRECOMMEND = "precommend"//추천필요여부
    var PDOCUMENT = "pdocument"//제출서류
    var infoList:List<String>

    constructor(list:List<String>){
        PID = list[0]
        PNUM = list[1]
        PINSTITUTION = list[2]
        PNAME = list[3]
        PINSTITUTION_D = list[4]
        P_D = list[5]
        PSCHOLARSHIP_D = list[6]
        PQUESTION = list[7]
        PUNIV_D = list[8]
        PGRADE_D = list[9]
        PDEPART_D = list[10]
        PSCORE = list[11]
        PINCOME = list[12]
        PSUPPORT = list[13]
        PQUALIFI = list[14]
        PAREA = list[15]
        PAPPLY_DATE = list[16]
        P_SELECT_WAY = list[17]
        P_SELECT_NUM = list[18]
        PQUALIFI_LIMIT = list[19]
        PRECOMMEND = list[20]
        PDOCUMENT = list[21]
        infoList = list
    }

    fun showMyData(){
        println("info size : "+infoList.size)
        for(i in 0 until infoList.size)
            if(infoList[i] == "")
                println("empty~")
            else
                println(infoList[i])
    }
}