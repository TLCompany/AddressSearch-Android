package com.tlsolution.tlsaddresssearchhelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_address_search.*
import kotlinx.android.synthetic.main.address_list_item.view.*
import okhttp3.*
import java.io.IOException

class AddressSearchActivity: AppCompatActivity() {

    companion object {
        val TAG = "AddressSearchActivity"
        val ADDRESS_KEY = "ADDRESS"
        val REQUEST_CODE = 1111
    }

    private var addresses: Array<Address>
        get() = adapter.addresses
        set(value) {
            adapter.addresses = value
            runOnUiThread {
                adapter.notifyDataSetChanged()
                val resultString = "검색결과: ${addresses.size}건"
                resultTextView.setText(resultString)
            }
        }

    private lateinit var adapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_search)

        supportActionBar?.hide()
        adapter = AddressAdapter()
        adapter.activity = this
        addressRecyclerView.adapter = this.adapter
        addressRecyclerView.layoutManager = LinearLayoutManager(this)

        backButton.setOnClickListener {
            finish()
        }

        searchTermEditText.setOnEditorActionListener(object: TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search()
                }
                return true
            }
        })

        searchButton.setOnClickListener {
            search()
        }

        logoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://www.tlsolution.co.kr/")
            startActivity(intent)
        }

        providerButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://www.juso.go.kr/addrlink/main.do")
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            val addressWithDetails = data.getSerializableExtra(AddDetailActivity.ADDRS_KEY) as? Address

            if (data.getBooleanExtra(AddDetailActivity.EXIT_KEY, false) == true &&
                addressWithDetails != null) {

                val data = Intent()
                data.putExtra(AddressSearchHelper.DATA_KEY, addressWithDetails)
                setResult(Activity.RESULT_OK, data)
                finish()
            }

        }
    }

    private val confmKey = "U01TX0FVVEgyMDE5MDQyOTE1MTYwNTEwODY5MDE="
    private val url = "http://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"

    private fun search() {

        val terms = searchTermEditText.text.toString()
        if (terms.isEmpty()) {
            Toast.makeText(this, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val client = OkHttpClient()
        val body = MultipartBody.Builder().addFormDataPart("confmKey", confmKey)
                                                            .addFormDataPart("keyword", terms)
                                                            .addFormDataPart("resultType", "json")
                                                            .addFormDataPart("currentPage", "1")
                                                            .addFormDataPart("countPerPage", "100")
                                                            .build()
        val request = Request.Builder().url(url).post(body).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body()?.string()?.replace("(", "")?.replace(")", "")
                Log.d(TAG, jsonString)
                if (jsonString == null) return
                val addressResult = GsonBuilder().create().fromJson(jsonString!!, AddressResponse::class.java)

                if (addressResult.results.juso == null || addressResult.results.juso.isEmpty()) {
                    runOnUiThread { Toast.makeText(this@AddressSearchActivity, "검색 된 결과가 없습니다.", Toast.LENGTH_SHORT).show() }
                } else {
                    addresses = addressResult.results.juso
                    dismissKeyboard()
                }
            }
        })
    }

    private fun dismissKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchTermEditText.windowToken, 0)
    }


    private class AddressAdapter(): RecyclerView.Adapter<AddressItemViewHolder>() {

        var addresses = arrayOf<Address>()
        lateinit var activity: Activity

        override fun getItemCount(): Int {
            return addresses.size
        }

        override fun onBindViewHolder(p0: AddressItemViewHolder, p1: Int) {
            val jbTextView = p0.customView.jbAddrsTextView2
            val rdTextView = p0.customView.zipCodeTextView

            jbTextView.text = addresses[p1]?.jibunAddr
            val part2 = if (addresses[p1]?.roadAddrPart2 == null) "" else "(${addresses[p1]?.roadAddrPart2!!.trim()})"
            rdTextView.text = "${addresses[p1]?.roadAddrPart1} ${part2}"
            p0.address = addresses[p1]

        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AddressItemViewHolder {
            val layoutInflator = LayoutInflater.from(p0.context)
            val rowCell = layoutInflator.inflate(R.layout.address_list_item, p0, false)
            return AddressItemViewHolder(rowCell, activity)
        }
    }

    private class AddressItemViewHolder(val customView: View, val activity: Activity): RecyclerView.ViewHolder(customView) {

        var address: Address? = null

        init {
            customView.setOnClickListener {
                if (address != null) {
                    val intent = Intent(customView.context, AddDetailActivity::class.java)
                    intent.putExtra(ADDRESS_KEY, address)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    activity.startActivityForResult(intent, AddressSearchActivity.REQUEST_CODE)
                }
            }
        }
    }
}
