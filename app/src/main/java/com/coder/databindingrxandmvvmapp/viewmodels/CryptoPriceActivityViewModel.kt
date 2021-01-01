package com.coder.databindingrxandmvvmapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coder.databindingrxandmvvmapp.`interface`.GetAllItems
import com.coder.databindingrxandmvvmapp.`interface`.GetData
import com.coder.databindingrxandmvvmapp.models.RetroCrypto
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CryptoPriceActivityViewModel: ViewModel() {
    private var myCompositeDisposable: CompositeDisposable? = null
    private var cryptoList: MutableLiveData<List<RetroCrypto>>
    init {
        cryptoList = MutableLiveData();
        myCompositeDisposable = CompositeDisposable()
    }
    fun getItemListObserver(): MutableLiveData<List<RetroCrypto>> {
        return cryptoList;
    }
    fun makeApiCall()  {
        val requestInterface = Retrofit.Builder()
            .baseUrl("https://api.nomics.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetData::class.java);
        requestInterface.getData("75d48fc8f769fcddd98ea7c2f429c8b8")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(getCryptoListObserverRx())
    }

    private fun getCryptoListObserverRx(): Observer<List<RetroCrypto>>{
        return object : Observer<List<RetroCrypto>> {
            override fun onComplete() {
            }
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: List<RetroCrypto>) {
                cryptoList.postValue(t);
            }

            override fun onError(e: Throwable) {
               cryptoList.postValue(null);
            }

        }

    }
}