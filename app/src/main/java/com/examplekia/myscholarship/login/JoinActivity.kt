package com.examplekia.myscholarship.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.examplekia.myscholarship.NetworkStatus
import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    lateinit var rdb: DatabaseReference
    lateinit var toast: Toast
    var result = false
    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        init()
        initBtn()
    }

    private fun initBtn(){
        btnJoin.setOnClickListener{ // 회원가입 클릭
            if(NetworkStatus().isConnectNetwork(applicationContext))
                if(name.text.isEmpty()){
                    toast.cancel()
                    toast = Toast.makeText(this, "이름을 입력해주세요.",Toast.LENGTH_SHORT)
                    toast.show()
                }else if(nickname.text.isEmpty()){
                    toast.cancel()
                    toast = Toast.makeText(this, "닉네임을 입력해주세요.",Toast.LENGTH_SHORT)
                    toast.show()
                }else if(email.text.isEmpty()){
                    toast.cancel()
                    toast = Toast.makeText(this, "이메일을 입력해주세요.",Toast.LENGTH_SHORT)
                    toast.show()
                }else if(!email.text.toString().contains("@") || !email.text.toString().contains(".")){
                    toast.cancel()
                    toast = Toast.makeText(this, "이메일 형식이 올바르지 않습니다.",Toast.LENGTH_SHORT)
                    toast.show()
                }else if(password.text.isEmpty()){
                    toast.cancel()
                    toast = Toast.makeText(this, "비밀번호를 입력해주세요.",Toast.LENGTH_SHORT)
                    toast.show()
                }else if(repassword.text.isEmpty()){
                    toast.cancel()
                    toast = Toast.makeText(this, "비밀번호를 한번 더 입력해주세요.",Toast.LENGTH_SHORT)
                    toast.show()
                }else if(password.text.length < 6){
                    toast.cancel()
                    toast = Toast.makeText(this, "비밀번호는 6글자 이상이어야 합니다.",Toast.LENGTH_SHORT)
                    toast.show()
                }else if(!password.text.toString().equals(repassword.text.toString())){
                    toast.cancel()
                    toast = Toast.makeText(this, "비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT)
                    toast.show()
                }else{
                    checkUser()
                }
            else{
                toast.cancel()
                toast = Toast.makeText(applicationContext,"인터넷 연결을 확인해주세요.",Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        btnLoginScreen.setOnClickListener{ // 로그인 하러 갈까요? 클릭
            this.finish()
            overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_right)
        }
    }

    private fun checkUser(){
        val email = email.text.toString()
        val password = password.text.toString()
        val name = name.text.toString()
        val nickname = nickname.text.toString()

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val user = FirebaseAuth.getInstance().currentUser

                    if(user != null){
                        val userInfo = UserData(name,nickname,user.uid,null,null)
                        rdb.child(user.uid).setValue(userInfo)
                        Toast.makeText(this, "회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show()
                        this.finish()
                    }
                }else{
                    toast.cancel()
                    toast = Toast.makeText(this,"이메일이 중복됩니다. 다시 입력해 주세요.",Toast.LENGTH_SHORT)
                    toast.show()
                    result = false
                }
            }
    }


    private fun init() {
        toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT

        mAuth = FirebaseAuth.getInstance()

        rdb = FirebaseDatabase.getInstance().getReference("Users")
    }
}
