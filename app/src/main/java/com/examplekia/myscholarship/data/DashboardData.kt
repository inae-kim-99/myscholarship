package com.examplekia.myscholarship.data

data class DashboardData
    (var name:String,
     var writer:String,
     var content:String,
     var date:String,
     var key:String,
     var email:String
)   {
    constructor():this("noname","writer","nocontent","nodate","key","email")
}