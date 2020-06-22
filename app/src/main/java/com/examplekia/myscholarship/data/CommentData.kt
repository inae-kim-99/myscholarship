package com.examplekia.myscholarship.data

data class CommentData
    (var writer:String,
     var content:String,
     var date:String,
     var key:String
)   {
    constructor():this("writer","nocontent","nodate","key")
}