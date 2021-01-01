package com.coder.databindingrxandmvvmapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coder.databindingrxandmvvmapp.`interface`.GetAllItems
import com.coder.databindingrxandmvvmapp.adapter.ItemAdapter
import com.coder.databindingrxandmvvmapp.models.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GetAllItemActivityViewModel: ViewModel() {
    private var myCompositeDisposable: CompositeDisposable? = null
    private var itemList: MutableLiveData<List<Item>>
    init {
        itemList = MutableLiveData();
        myCompositeDisposable = CompositeDisposable()
    }
    fun getItemListObserver(): MutableLiveData<List<Item>> {
        return itemList;
    }
    fun makeApiCall()  {
        val requestInterface = Retrofit.Builder()
            .baseUrl("https://easy-business-app.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetAllItems::class.java);
            requestInterface.getItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getItemListObserverRx())
    }
    private fun getItemListObserverRx(): io.reactivex.Observer<List<Item>> {
        return object : io.reactivex.Observer<List<Item>> {
            override fun onComplete() {
                Log.d("oncomplete::::", "Completed")

            }
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: List<Item>) {
                val ex = ArrayList(t);
                Log.d("onNext::::", t.toString())
                Log.d("onNext::::", ex.toString())
               itemList.postValue(t);
            }

            override fun onError(e: Throwable) {
                Log.d("onerror::::", e.toString())
                itemList.postValue(null);
            }

        }

    }
}

