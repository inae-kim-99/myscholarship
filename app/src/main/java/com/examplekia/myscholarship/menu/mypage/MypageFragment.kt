package com.examplekia.myscholarship.menu.mypage

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.examplekia.myscholarship.R
import com.examplekia.myscholarship.login.LoginActivity
import com.examplekia.myscholarship.tutorial.TutorialActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_mypage.*

/**
 * A simple [Fragment] subclass.
 */
class MypageFragment : Fragment() {

    val user = FirebaseAuth.getInstance().currentUser!!
    val rdb = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initBtn()
    }

    private fun initBtn() {

        //로그아웃
        logoutBtn.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle("로그아웃")
            builder.setMessage("로그아웃 하시겠습니까?")

            builder.setPositiveButton("네",object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    dialog?.dismiss()
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(requireContext(),LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    Toast.makeText(requireContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show()
                }
            })

            builder.setNegativeButton("취소",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }

            })

            builder.show()
        }

        //닉네임 변경
        changeNicknameBtn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle("닉네임 변경")
            builder.setMessage("변경할 닉네임을 작성해 주세요.")
            val edit = EditText(requireContext())
            builder.setView(edit)

            builder.setPositiveButton("변경하기",object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    rdb.orderByChild("uid").equalTo(user.uid).addListenerForSingleValueEvent(object:ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for(item in p0.children){
                                val rd = item.child("unickname").ref
                                rd.setValue(edit.text.toString())
                                nicknameText.text = "닉네임 : "+edit.text.toString()
                            }
                        }
                    })
                    dialog?.dismiss()
                    Toast.makeText(requireContext(),"닉네임이 "+edit.text.toString()+"(으)로 변경되었습니다.",Toast.LENGTH_SHORT).show()
                }
            })

            builder.setNegativeButton("취소",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                   dialog?.dismiss()
                }

            })

            builder.show()
        }

        //내가 쓴 게시물
        myDashboardBtn.setOnClickListener {
            val intent = Intent(requireContext(),MyDashboardActivity::class.java)
            startActivity(intent)
        }

        //오픈소스 라이센스
//        lisenseBtn.setOnClickListener {
//            val builder = AlertDialog.Builder(requireContext())
//
//            builder.setTitle("오픈소스 라이센스")
//            val text = TextView(requireContext())
//
//            //text
//
//            builder.setView(text)
//        }

        tutorialBtn.setOnClickListener {
            val intent = Intent(requireContext(), TutorialActivity::class.java)
            startActivity(intent)
        }

    }

    private fun init() {


        rdb.orderByChild("uid").equalTo(user.uid).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(item in p0.children){
                    val nickname = item.child("unickname").getValue().toString()
                    val name = item.child("uname").getValue().toString()
                    val email = user.email!!

                    nameText.text = "이름 : "+name
                    nicknameText.text = "닉네임 : "+nickname
                    emailText.text = "이메일 : "+email
                }

            }

        })

    }

}
