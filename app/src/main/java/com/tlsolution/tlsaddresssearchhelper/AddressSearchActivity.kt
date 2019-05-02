package com.tlsolution.tlsaddresssearchhelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AddressSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_search)

        supportActionBar?.hide()
    }
}
