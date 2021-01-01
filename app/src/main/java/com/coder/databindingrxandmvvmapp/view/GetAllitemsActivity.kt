package com.coder.databindingrxandmvvmapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.coder.databindingrxandmvvmapp.R
import com.coder.databindingrxandmvvmapp.adapter.ItemAdapter
import com.coder.databindingrxandmvvmapp.models.Item
import com.coder.databindingrxandmvvmapp.viewmodels.GetAllItemActivityViewModel
import kotlinx.android.synthetic.main.activity_get_allitems.*
import java.lang.Exception

class GetAllitemsActivity : AppCompatActivity(),ItemAdapter.Listener {

    private var itemAdapter: ItemAdapter? = null
//    private var myCompositeDisposable: CompositeDisposable? = null
    private var myItemList: ArrayList<Item>? = null
    private var spinner:ProgressBar? = null

    //view model
    lateinit var viewModel: GetAllItemActivityViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_allitems)
//        myCompositeDisposable = CompositeDisposable()
        spinner = findViewById(R.id.progressBar) as ProgressBar;
        initRecyclerView()
        try {
//            loadStoreItems()
                loadItemsMVVM()
        } catch (ex: Exception) {
            Log.d("Exception: ", ex.toString());
        }
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this);
        item_recycler_list.layoutManager = layoutManager;
    }

    //MVVM implementation with view model
    private fun loadItemsMVVM() {
       viewModel= ViewModelProvider(this).get(GetAllItemActivityViewModel::class.java)
        viewModel.getItemListObserver().observe(this, Observer<List<Item>> {
            if(it != null) {
                //update adapter
                myItemList = ArrayList(it);
                Log.d("loadItemsMVVM it not null", myItemList.toString());
                itemAdapter = ItemAdapter(myItemList!!,this);
                item_recycler_list.adapter = itemAdapter;
                spinner?.visibility = View.GONE;
            } else {
                Log.d("loadItemsMVVM Error", "null")
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        });
        viewModel.makeApiCall(); //making api call from view model
    }

    //end of mvvm implementation

//    //Implement oadStoreItems for getAllitems api//
//    private fun loadStoreItems() {
//        val requestInterface = Retrofit.Builder()
//            .baseUrl("https://easy-business-app.herokuapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build().create(GetAllItems::class.java);
//        myCompositeDisposable?.add(
//            requestInterface.getItem()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleItemResponse)
//        )
//    }
//    //handle response for getAllItemItems
//    private fun handleItemResponse(itemList:List<Item>) {
//        Log.d("handleItemResponse", itemList.toString())
//        myItemList = ArrayList(itemList);
//        itemAdapter = ItemAdapter(myItemList!!,this);
//        item_recycler_list.adapter = itemAdapter;
//        Log.d("Size", itemList.size.toInt().toString())
//        if( itemList.size.toInt() !=0) {
//            Log.d("inside if:::::: ","IF")
//            spinner?.visibility = View.GONE;
//        }
//    }

    override fun onStoreItemClick(item: Item) {
        Toast.makeText(this, "You clicked: ${item.itemName}", Toast.LENGTH_LONG).show()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        //Clear all your disposables//
//        myCompositeDisposable?.clear()
//    }
}