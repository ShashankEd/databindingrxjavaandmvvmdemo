package com.coder.databindingrxandmvvmapp.`interface`

import com.coder.databindingrxandmvvmapp.classes.Item
import io.reactivex.Observable
import retrofit2.http.GET

interface GetAllItems {
    @GET("getAllItems")
    fun getItem() : Observable<List<Item>>
}