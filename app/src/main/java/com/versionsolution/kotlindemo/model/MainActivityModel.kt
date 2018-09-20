package com.kotlin.kotlinprojectbase.model

import com.kotlin.kotlinprojectbase.contract.MainContract

class MainActivityModel : MainContract.Model {

    override fun getData(jsonObject: String): String {
        return jsonObject
    }

    /*   @Override
    public String setData(String s) {
        return null;
    }*/
}