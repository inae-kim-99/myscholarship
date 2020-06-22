package com.examplekia.myscholarship.menu.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.DashboardData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_write_board.*
import java.util.*

class WriteBoardActivity : AppCompatActivity() {

    val REQUEST_CODE = 0
    lateinit var rdb: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_board)
        init()

    }

    private fun init() {
        rdb = FirebaseDatabase.getInstance().getReference("Dashboards/items")


        submitBtn.setOnClickListener {
            if(nameEdit.text.isEmpty()){
                Toast.makeText(this,"제목을 입력해주세요.",Toast.LENGTH_SHORT)
            }else if(contentEdit.text.isEmpty()){
                Toast.makeText(this,"내용을 입력해주세요.",Toast.LENGTH_SHORT)
            }else{
                val user = FirebaseAuth.getInstance().currentUser!!
                val u = FirebaseDatabase.getInstance().getReference("Users")
                u.child(user.uid).addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
//                TODO("Not yet implemented")
                        val item = p0.child("unickname").getValue().toString()
                        makeBoard(item,user.email!!)
                    }

                })

            }
        }

        closeBtn_write.setOnClickListener {
            this.finish()
            overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right)
        }
    }

    private fun makeBoard(writer:String,email:String) {
        val rdb = FirebaseDatabase.getInstance().getReference("Dashboards")
        val key = FirebaseDatabase.getInstance().getReference("Dashboards").push().key!!
        //날짜
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = (date.get(Calendar.MONTH)+1).toString()
        val day = date.get(Calendar.DATE).toString()
        val hour = date.get(Calendar.HOUR_OF_DAY).toString()
        val minute = date.get(Calendar.MINUTE).toString()
        var dateStr = year+"."+month+"."+day+"  "+hour+":"+minute

        val item = DashboardData(nameEdit.text.toString(),writer,contentEdit.text.toString(),dateStr,key,email)
        rdb.child(key).setValue(item)
        Toast.makeText(this,"게시글을 작성했습니다.", Toast.LENGTH_SHORT).show()
        this.finish()
    }
}
