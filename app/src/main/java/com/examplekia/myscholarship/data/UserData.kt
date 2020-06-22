package com.examplekia.myscholarship.data

data class UserData
    (var uName:String,
     var uNickname:String,
     var uId:String,
     var favoriteScholarList:List<String>?,
     var favoriteServiceList:List<ServiceData>?
)   {
    constructor():this("name","nickname","id",null,null)
}