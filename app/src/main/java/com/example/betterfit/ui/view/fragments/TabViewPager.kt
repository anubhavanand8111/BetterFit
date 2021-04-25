package com.example.betterfit.ui.view.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabViewPager(fm:FragmentManager):FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList=ArrayList<String>()

    override fun getCount(): Int = mFragmentList.size

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? = mFragmentTitleList[position]

    fun addFragment(fragment: Fragment,title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

}