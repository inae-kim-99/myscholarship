package com.examplekia.myscholarship.menu.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.examplekia.myscholarship.MyDBHelper

import com.examplekia.myscholarship.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_bookmark.*

/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : Fragment() {

    lateinit var myDBHelper: MyDBHelper
    var tabTitle = arrayListOf<String>("장학금","학생지원")
    lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myDBHelper = MyDBHelper(requireContext())
        init()
//        recyclerView_favorite.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView_favorite.adapter = MyFavoriteAdapter(myDBHelper.getFavoriteList(),requireContext())
    }

    private fun init() {
        //init viewpager
        adapter = ViewPagerAdapter(
            requireContext(),
            tabTitle
        )
        viewpager.adapter = adapter

        TabLayoutMediator(tabLayout, viewpager, object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                tab.setText(tabTitle[position])

            }
        }).attach()
    }

}
