package com.examplekia.myscholarship.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.examplekia.myscholarship.MainActivity
import com.examplekia.myscholarship.NetworkStatus
import com.examplekia.myscholarship.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.showtimetable.tutorial.TutorialActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var rdb: DatabaseReference
    lateinit var auth : FirebaseAuth
    lateinit var toast:Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this,TutorialActivity::class.java)
        startActivity(intent)

        init()
        //로그인
        sign_in_button.setOnClickListener {
            // 아이디 비밀번호 확인
            if(NetworkStatus().isConnectNetwork(applicationContext))
                checkUser()
            else{
                toast.cancel()
                toast = Toast.makeText(applicationContext,"인터넷 연결을 확인해주세요.",Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        //회원가입
        sign_up_button.setOnClickListener {
            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left)
        }
    }

    private fun init() {
        rdb = FirebaseDatabase.getInstance().getReference("Users")
        auth = FirebaseAuth.getInstance()
        toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
    }

    private fun checkUser() {
        val email = login_id.text.toString().trim()
        val password = login_password.text.toString().trim()

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    toast.cancel()
                    toast = Toast.makeText(this,"회원 정보를 확인해 주세요.",Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
    }
}
