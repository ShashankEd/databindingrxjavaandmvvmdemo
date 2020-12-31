package com.coder.databindingrxandmvvmapp.`interface`

import android.content.res.Resources
import com.coder.databindingrxandmvvmapp.classes.RetroCrypto
import io.reactivex.Observable
import retrofit2.http.GET

interface GetData {
    @GET("prices?key=75d48fc8f769fcddd98ea7c2f429c8b8")
    fun getData() : Observable<List<RetroCrypto>>
}