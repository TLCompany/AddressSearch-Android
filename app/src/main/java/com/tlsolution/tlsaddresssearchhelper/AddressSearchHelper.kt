package com.tlsolution.tlsaddresssearchhelper

import android.app.Activity
import android.content.Intent
import android.util.Log

/**
 *  대한민국 주소 검색을 위한 Helper
 */
open class AddressSearchHelper {

    private var activity: Activity? = null

    /**
     * 한번 검색에 최대 몇 개의 결과값(max=100)
     */
    var countPerPage = 100
    /**
     * 한국지역개발원에서 발행하는 주소 검색 API Key
     */
    var confmKey: String? = null

    constructor(activity: Activity) {
        this.activity = activity
    }

    companion object {
        val REQUEST_CODE_KEY = 1010
        val DATA_KEY = "ADDRESS"
        val TAG = "AddressSearchHelper"
    }

    /**
     * 주소 검색을 시작한다.
     */
    fun startSearchingAddress() {

        if (activity == null) {
            Log.d(TAG, "😱 Activity is null")
            return
        }

        if (confmKey == null) {
            Log.d(TAG, "😱 confmKey is null")
            return
        }

        val intent = Intent(activity?.baseContext, AddressSearchListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        activity?.startActivityForResult(intent, REQUEST_CODE_KEY)
    }
}










































