package com.coder.databindingrxandmvvmapp.`interface`

import com.coder.databindingrxandmvvmapp.models.RetroCrypto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GetData {
//    @GET("prices?key=75d48fc8f769fcddd98ea7c2f429c8b8")
    @GET("prices")
    fun getData(@Query("key") query: String) : Observable<List<RetroCrypto>>
}