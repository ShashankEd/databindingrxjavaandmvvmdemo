package com.coder.databindingrxandmvvmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar

import android.widget.Toast
import com.coder.databindingrxandmvvmapp.adapter.MyAdapter
import com.coder.databindingrxandmvvmapp.classes.RetroCrypto

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.coder.databindingrxandmvvmapp.`interface`.GetData
import kotlinx.android.synthetic.main.activity_crypto_price.*
import java.lang.Exception

class CryptoPriceActivity : AppCompatActivity(),MyAdapter.Listener {

    private var myAdapter: MyAdapter? = null
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myRetroCryptoArrayList: ArrayList<RetroCrypto>? = null
    private var spinner: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_price)
        spinner = findViewById(R.id.progressBar) as ProgressBar;
        myCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        try {
            loadData()
        } catch (e: Exception) {
            Log.d("GSON ", e.toString());
        }
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this);
        cryptocurrency_list.layoutManager = layoutManager;
    }

    //Implement loadData for crypto api//
    private fun loadData() {
        //Define the Retrofit request//
        val requestInterface = Retrofit.Builder()
            .baseUrl("https://api.nomics.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetData::class.java)
        //Add all RxJava disposables to a CompositeDisposable//
        myCompositeDisposable?.add(
            requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    //for crypto api
    private fun handleResponse(cryptoList: List<RetroCrypto>) {
        myRetroCryptoArrayList = ArrayList(cryptoList)
        myAdapter = MyAdapter(myRetroCryptoArrayList!!, this) // through this listener CryptoPriceActivity and MyAdapter will communicate
        //Set the adapter to the recycler view//
        cryptocurrency_list.adapter = myAdapter
        //once response comes then disable the progressbar
        if( cryptoList.size.toInt() !=0) {
            spinner?.visibility = View.GONE;
        }
    }

    override fun onItemClick(retroCrypto: RetroCrypto) {
        Toast.makeText(this, "You clicked: ${retroCrypto.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //Clear all your disposables//
        myCompositeDisposable?.clear()
    }
}