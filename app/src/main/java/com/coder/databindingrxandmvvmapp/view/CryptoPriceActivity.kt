package com.coder.databindingrxandmvvmapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coder.databindingrxandmvvmapp.adapter.MyAdapter
import com.coder.databindingrxandmvvmapp.models.RetroCrypto

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.coder.databindingrxandmvvmapp.R
import com.coder.databindingrxandmvvmapp.viewmodels.CryptoPriceActivityViewModel
import kotlinx.android.synthetic.main.activity_crypto_price.*
import java.lang.Exception

class CryptoPriceActivity : AppCompatActivity(),MyAdapter.Listener {

    private var myAdapter: MyAdapter? = null
//    private var myCompositeDisposable: CompositeDisposable? = null
    private var myRetroCryptoArrayList: ArrayList<RetroCrypto>? = null
    private var spinner: ProgressBar? = null
    lateinit var viewModel: CryptoPriceActivityViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_price)
        spinner = findViewById(R.id.progressBar) as ProgressBar;
//        myCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        try {
//            loadData()
            loadDataMvvm()
        } catch (e: Exception) {
            Log.d("GSON ", e.toString());
        }
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this);
        cryptocurrency_list.layoutManager = layoutManager;
    }

    //implement the nework call and updating view using mvvm architecture through viewmodel
    private fun loadDataMvvm() {
        viewModel = ViewModelProvider(this).get(CryptoPriceActivityViewModel::class.java);
        viewModel.getItemListObserver().observe(this,Observer<List<RetroCrypto>> {
            if(it != null) {
                myRetroCryptoArrayList = ArrayList(it)
                myAdapter = MyAdapter(myRetroCryptoArrayList!!, this) // through this listener CryptoPriceActivity and MyAdapter will communicate
                //Set the adapter to the recycler view//
                cryptocurrency_list.adapter = myAdapter
                spinner?.visibility = View.GONE;
            } else {

            }
        })
        viewModel.makeApiCall()
    }
//    //Implement loadData for crypto api//
//    private fun loadData() {
//        //Define the Retrofit request//
//        val requestInterface = Retrofit.Builder()
//            .baseUrl("https://api.nomics.com/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build().create(GetData::class.java)
//        //Add all RxJava disposables to a CompositeDisposable//
//        myCompositeDisposable?.add(
//            requestInterface.getData("75d48fc8f769fcddd98ea7c2f429c8b8")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse)
//        )
//    }
//
//    //for crypto api
//    private fun handleResponse(cryptoList: List<RetroCrypto>) {
//        myRetroCryptoArrayList = ArrayList(cryptoList)
//        myAdapter = MyAdapter(myRetroCryptoArrayList!!, this) // through this listener CryptoPriceActivity and MyAdapter will communicate
//        //Set the adapter to the recycler view//
//        cryptocurrency_list.adapter = myAdapter
//        //once response comes then disable the progressbar
//        if( cryptoList.size.toInt() !=0) {
//            spinner?.visibility = View.GONE;
//        }
//    }

    override fun onItemClick(retroCrypto: RetroCrypto) {
        Toast.makeText(this, "You clicked: ${retroCrypto.currency}", Toast.LENGTH_LONG).show()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        //Clear all your disposables//
//        myCompositeDisposable?.clear()
//    }
}