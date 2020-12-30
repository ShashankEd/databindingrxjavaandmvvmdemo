package com.coder.databindingrxandmvvmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.coder.databindingrxandmvvmapp.classes.User
import com.coder.databindingrxandmvvmapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   lateinit var lateBinding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        bindData();
    }
    //method which binds the daya to view, it is late binding, as the value is not being assigned at the on-create of the activity
    private fun bindData() {
        lateBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        var userObj = User();
        userObj.uName = "shashank";
        userObj.pwd ="12345";
        lateBinding.userModel = userObj;
    }
}