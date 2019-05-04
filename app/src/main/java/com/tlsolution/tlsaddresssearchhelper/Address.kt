package com.tlsolution.tlsaddresssearchhelper

import java.io.Serializable
import java.util.ArrayList

class Address(val jibunAddr: String,
              val roadFullAddr: String,
              val roadAddrPart1: String,
              val roadAddrPart2: String?,
              val engAddr: String,
              val zipNo: String,
              val siNm: String,
              val sggNm: String,
              val emdNm: String,
              val liNm: String,
              val rn: String,
              val lnbrMnnm: String,
              val lnbrSlno: String,
              var detail: String? = null): Serializable {
    var rdAddr: String? = null
    get() {

        return "${this.roadAddrPart1} ${if (this.roadAddrPart2 == null) "" else "(${this.roadAddrPart2!!.trim()})"}"
    }
}

class Result(val errorMessage: String, val countPerPage: String, val totalCount: String, val errorCode: String, val currentPage: String)
class Results(val common: Result, val juso: Array<Address>?)
class AddressResponse(val results: Results)