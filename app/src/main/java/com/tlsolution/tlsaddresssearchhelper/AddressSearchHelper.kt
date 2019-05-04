package com.tlsolution.tlsaddresssearchhelper

import android.app.Activity
import android.content.Intent
import android.util.Log

class AddressSearchHelper {

    private var activity: Activity? = null

    var countPerPage = 100
    var confmKey: String? = null

    constructor(activity: Activity) {
        this.activity = activity
    }

    companion object {

        val REQUEST_CODE_KEY = 1010
        val DATA_KEY = "ADDRESS"
        val TAG = "AddressSearchHelper"
    }

    fun startSearchingAddress() {
        if (activity == null) {
            Log.d(TAG, "Activity is null")
            return
        }

        if (confmKey == null) {
            Log.d(TAG, "confmKey is null")
            return
        }

        val intent = Intent(activity?.baseContext, AddressSearchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        activity?.startActivityForResult(intent, REQUEST_CODE_KEY)
    }
}










































