package com.tlsolution.tlsaddresssearchhelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var addressSearchHelper: AddressSearchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressSearchHelper = AddressSearchHelper(this)

        searchButton.setOnClickListener {

            addressSearchHelper.startSearchingAddress()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val address = data?.getSerializableExtra(AddressSearchHelper.DATA_KEY) as? Address
        if (requestCode == AddressSearchHelper.REQUEST_CODE_KEY && address != null) {
            Log.d("Address", address.jibunAddr.toString())
            val addressString = "${address.jibunAddr}\n${address.rdAddr}\n${address.engAddr}\n우편번호: ${address.zipNo}"
            addressTextView.setText(addressString)
        }
    }
}
