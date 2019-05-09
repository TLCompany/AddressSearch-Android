package com.tlsolution.tlsaddresssearchhelper

import java.io.Serializable

/**
 * 주소 데이터 모델
 * @property jibunAddr 전체주소(번지)
 * @property roadAddrPart1 도로명 주소 Part 1
 * @property roadAddrPart2 도로명 주소 Part 2
 * @property engAddr 전체주소(영어)
 * @property zipNo 우편번호
 * @property siNm 시 이름
 * @property sggNm 시군구
 * @property emdNm 읍면동 명
 * @property liNm 법정리 명
 * @property rn 도로명
 * @property lnbrMnnm 번지
 * @property lnbrSlno 호
 * @property detail 상세주소(사용자입력)
 */
public class Address(val jibunAddr: String,
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
    /**
     * 전체주소(도로명)
     */
    var roadAddr: String? = null
    get() {

        return "${this.roadAddrPart1} ${if (this.roadAddrPart2 == null) "" else "(${this.roadAddrPart2!!.trim()})"}"
    }
}

class Result(val errorMessage: String, val countPerPage: String, val totalCount: String, val errorCode: String, val currentPage: String)
class Results(val common: Result, val juso: Array<Address>?)
class AddressResult(val results: Results)