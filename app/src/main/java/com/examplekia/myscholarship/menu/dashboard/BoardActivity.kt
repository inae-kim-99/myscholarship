package com.examplekia.myscholarship.menu.dashboard

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.CommentData
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_board.content_board
import kotlinx.android.synthetic.main.activity_board.name_board
import java.util.*

class BoardActivity : AppCompatActivity() {

    var key =""
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter:CommentAdapter
    lateinit var rdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        init()
    }

    private fun init() {
        recyclerView_comment.isNestedScrollingEnabled = false
        val intent = getIntent()
        key = intent.getStringExtra("key")!!
        println(key)
        setBoard()

        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView_comment.layoutManager = layoutManager
//        rdb = FirebaseDatabase.getInstance().getReference("Dashboards").child(key)
        val query = FirebaseDatabase.getInstance().reference.child("Dashboards").child(key).child("Comments").limitToLast(100)
        val option = FirebaseRecyclerOptions.Builder<CommentData>()
            .setQuery(query,CommentData::class.java)
            .build()
        adapter = CommentAdapter(option,this)
//        adapter.itemClickListener = object : MyDashboardAdapter.OnItemClickListener{
//            override fun OnItemClick(view: View, position: Int) {
//                val intent = Intent(requireContext(),BoardActivity::class.java)
//                intent.putExtra("position",position)
//                startActivity(intent)
//            }
//        }
        recyclerView_comment.adapter = adapter


        closeBtn_board.setOnClickListener {
            this.finish()
            overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right)
        }



        addCommentBtn.setOnClickListener{
            if(commentEdit.text.isNotEmpty()){
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(commentEdit.windowToken,0)
                val user = FirebaseAuth.getInstance().currentUser!!
                val u = FirebaseDatabase.getInstance().getReference("Users")
                u.child(user.uid).addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
//                TODO("Not yet implemented")
                        val item = p0.child("unickname").getValue().toString()
                        makeComment(item)
                        commentEdit.text.clear()
                    }

                })
            }
        }

    }

    private fun makeComment(writer:String) {
        //val name =nickname.get
        val rdb = FirebaseDatabase.getInstance().getReference("Dashboards").child(key)
        val comment_key = FirebaseDatabase.getInstance().getReference("Dashboards").child(key).child("Comments").push().key!!
        //날짜
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = (date.get(Calendar.MONTH)+1).toString()
        val day = date.get(Calendar.DATE).toString()
        val hour = date.get(Calendar.HOUR_OF_DAY).toString()
        val minute = date.get(Calendar.MINUTE).toString()
        var dateStr = year+"."+month+"."+day+"  "+hour+":"+minute

        val item = CommentData(writer,commentEdit.text.toString(),dateStr,comment_key)
        rdb.child("Comments").child(comment_key).setValue(item)
        Toast.makeText(this,"댓글을 작성했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun setBoard() {
        val rdb = FirebaseDatabase.getInstance().getReference("Dashboards")
        rdb.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(i in p0.children){
                    val k = i.child("key").getValue().toString()
                    if(key == k){
                        name_board.text = i.child("name").getValue().toString()
                        content_board.text = i.child("content").getValue().toString()
                        day_board.text = i.child("date").getValue().toString()
                        writer_board.text = i.child("writer").getValue().toString()
                    }
                }

            }
        })
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
