package com.versionsolution.kotlindemo.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.versionsolution.kotlindemo.R


class HomeFragment : Fragment() {


    private lateinit var rootView:View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_home, container, false)

        return rootView


    }
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

}
