package com.coder.databindingrxandmvvmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.coder.databindingrxandmvvmapp.`interface`.GetAllItems
import com.coder.databindingrxandmvvmapp.adapter.ItemAdapter
import com.coder.databindingrxandmvvmapp.classes.Item
import kotlinx.android.synthetic.main.activity_get_allitems.*
import java.lang.Exception

class GetAllitemsActivity : AppCompatActivity(),ItemAdapter.Listener {

    private var itemAdapter: ItemAdapter? = null
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myItemList: ArrayList<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_allitems)
        myCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        try {
            loadStoreItems()
        } catch (ex: Exception) {
            Log.d("Exception: ", ex.toString());
        }
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this);
        item_recycler_list.layoutManager = layoutManager;
    }

    //Implement oadStoreItems for getAllitems api//
    private fun loadStoreItems() {
        val requestInterface = Retrofit.Builder()
            .baseUrl("https://easy-business-app.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetAllItems::class.java);
        myCompositeDisposable?.add(
            requestInterface.getItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleItemResponse)
        )
    }
    //handle response for getAllItemItems
    private fun handleItemResponse(itemList:List<Item>) {
        myItemList = ArrayList(itemList);
        itemAdapter = ItemAdapter(myItemList!!,this);
        item_recycler_list.adapter = itemAdapter;
    }

    override fun onStoreItemClick(item: Item) {
        Toast.makeText(this, "You clicked: ${item.itemName}", Toast.LENGTH_LONG).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        //Clear all your disposables//
        myCompositeDisposable?.clear()
    }
}