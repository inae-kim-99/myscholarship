package com.examplekia.myscholarship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.examplekia.myscholarship.menu.bookmark.BookmarkFragment
import com.examplekia.myscholarship.menu.dashboard.DashboardFragment
import com.examplekia.myscholarship.menu.mypage.MypageFragment
import com.examplekia.myscholarship.menu.scholarship.ScholarFragment
import com.examplekia.myscholarship.menu.support.ServiceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fm:FragmentManager
    lateinit var ft:FragmentTransaction

    lateinit var myDBHelper: MyDBHelper

    lateinit var scholarFragment:ScholarFragment
    lateinit var bookmarkFragment:BookmarkFragment
    lateinit var mypageFragment: MypageFragment
    lateinit var serviceFragment:ServiceFragment
    lateinit var dashboardFragment:DashboardFragment
    lateinit var toast:Toast


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        myDBHelper = MyDBHelper(this)
        toast = Toast(this)

        bottomNavigationView.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when(p0.itemId){
                    R.id.action_scholarship->setFragment(0)
                    R.id.action_support->{
                        if(NetworkStatus().isConnectNetwork(applicationContext))
                            setFragment(1)
                        else{
                            toast.cancel()
                            toast = Toast.makeText(applicationContext,"인터넷 연결을 확인해주세요.",Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                    R.id.action_bookmark->{
                        if(NetworkStatus().isConnectNetwork(applicationContext))
                            setFragment(2)
                        else{
                            toast.cancel()
                            toast = Toast.makeText(applicationContext,"인터넷 연결을 확인해주세요.",Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                    R.id.action_mypage->{
                        if(NetworkStatus().isConnectNetwork(applicationContext))
                            setFragment(3)
                        else{
                            toast.cancel()
                            toast = Toast.makeText(applicationContext,"인터넷 연결을 확인해주세요.",Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                    R.id.action_dashboard->{
                        if(NetworkStatus().isConnectNetwork(applicationContext))
                            setFragment(4)
                        else{
                            toast.cancel()
                            toast = Toast.makeText(applicationContext,"인터넷 연결을 확인해주세요.",Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }
                return true
            }
        })

        scholarFragment = ScholarFragment()
        bookmarkFragment = BookmarkFragment()
        mypageFragment = MypageFragment()
        serviceFragment= ServiceFragment()
        dashboardFragment= DashboardFragment()
        setFragment(0)
//        test()
    }

    private fun setFragment(n:Int){
        fm = supportFragmentManager
        ft = fm.beginTransaction()
        when(n){
            0->ft.replace(R.id.frameLayout,scholarFragment).commit()
            1->ft.replace(R.id.frameLayout,serviceFragment).commit()
            2->ft.replace(R.id.frameLayout,bookmarkFragment).commit()
            3->ft.replace(R.id.frameLayout,mypageFragment).commit()
            4->ft.replace(R.id.frameLayout,dashboardFragment).commit()
        }
    }

    fun test(){
        var testList = myDBHelper.testDB()
        var resultList = ArrayList<ArrayList<String>>()
        for(i in 0 until 20)
            resultList.add(ArrayList<String>())
        for(i in 0 until testList.size){
            next@for(j in 0 until testList[i].size){
                println(testList[i][j])
                if(testList[i][j] != null && testList[i][j] != ""){
                    val split = testList[i][j].replace(" ", "")
                    var split2 = split.split(",")
                    next2@for(u in 0 until split2.size){
                        for(k in 0 until resultList[i].size){
                            if(resultList[i][k] == split2[u])
                                continue@next2
                        }
                        resultList[i].add(split2[u])
                    }
                }
            }
        }

        for(i in 0 until resultList.size){
            println("INFO "+(i+1))
            next@for(j in 0 until resultList[i].size){
                print(resultList[i][j]+", ")
            }
            println("\n")
        }
    }

}
