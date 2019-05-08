package com.tlsolution.tlsaddresssearchhelper

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_detail.*

class AddressDetailActivity : AppCompatActivity() {

    private var address: Address? = null

    companion object {
        val TAG = "AddressDetailActivity"
        val EXIT_KEY = "EXIT"
        val ADDRS_KEY = "ADDRESS"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_detail)
        supportActionBar?.hide()
        this.address = intent.getSerializableExtra(AddressSearchListActivity.ADDRESS_KEY) as Address
        Log.d(TAG, this.address.toString())
        displayAddress(this.address!!)
        backButton.setOnClickListener {
            finish()
        }


        completeButton.setOnClickListener {
            if (address == null) return@setOnClickListener

            val detail = searchTermEditText.text.toString()
            address!!.detail = detail
            Log.d(TAG, address.toString())

            val data = Intent()
            data.putExtra(EXIT_KEY, true)
            data.putExtra(ADDRS_KEY, this.address)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun displayAddress(address: Address) {
        jbAddrsTextView2.setText(address.jibunAddr)
        rdAddrsTextView2.setText(address.roadAddr)
        val zipCode = "우편번호: ${address.zipNo}"
        zipCodeTextView.setText(zipCode)
    }

}
