package com.tlsolution.tlsaddresssearchhelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class AddressExampleActivity : AppCompatActivity() {

    lateinit var addressSearchHelper: AddressSearchHelper
    private val confmKey = "U01TX0FVVEgyMDE5MDQyODE5NTM0NjEwODY4ODQ="

    companion object {
        val TAG = "AddressExampleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        title = "대한민국 주소 검색 Android Library"
        addressSearchHelper = AddressSearchHelper(this)
        addressSearchHelper.confmKey = confmKey

        searchButton.setOnClickListener {
            addressSearchHelper.startSearchingAddress()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val address = data?.getSerializableExtra(AddressSearchHelper.DATA_KEY) as? Address

        if (requestCode != AddressSearchHelper.REQUEST_CODE_KEY || address == null) {
            Log.d(TAG, "😭 Failed to get the result data of the searched address...")
            return
        }

        Log.d(TAG, "😀 Succeeded in getting the result data of the searched address!")
        val addressString = "${address!!.jibunAddr}\n${address.roadAddr}\n${address.engAddr}\n우편번호: ${address.zipNo}"
        addressTextView.setText(addressString)
    }
}
