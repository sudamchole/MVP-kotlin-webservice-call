package com.versionsolution.kotlindemo.view.activities

import android.os.Bundle
import android.widget.Toast
import com.kotlin.kotlinprojectbase.BaseDrawerActivity
import com.kotlin.kotlinprojectbase.contract.MainContract
import com.kotlin.kotlinprojectbase.presenter.MainActivityPresenter
import com.versionsolution.kotlindemo.R

class MainActivity : BaseDrawerActivity(), MainContract.View{

    private lateinit var presenter: MainContract.Presenter
    internal var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_main, content_frame)

        //Webservice call

        presenter = MainActivityPresenter(this)
        id = 2
        val s = arrayOf("sudam")
        presenter.onClick(2, s,MainActivity@this,"0")
    }

    override fun initView() {
//        presenter = MainActivityPresenter(this)
//

    }

    override fun setViewData(data: String) {
        //Handle your web service response here
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }
}
