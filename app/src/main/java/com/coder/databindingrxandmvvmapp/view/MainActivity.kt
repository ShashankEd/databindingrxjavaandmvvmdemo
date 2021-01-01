package com.coder.databindingrxandmvvmapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.coder.databindingrxandmvvmapp.R
import com.coder.databindingrxandmvvmapp.classes.User
import com.coder.databindingrxandmvvmapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   lateinit var lateBinding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        bindData();
        moveToCryptoScreen();
    }

    private fun moveToCryptoScreen() {
        var btn = findViewById(R.id.button) as Button;
        var btn2 = findViewById(R.id.button2) as Button;
        btn.setOnClickListener {
            val intent = Intent(applicationContext,
                CryptoPriceActivity::class.java).apply{
            }
            startActivity(intent);
        }
        btn2.setOnClickListener {
            val intent = Intent(applicationContext,
                GetAllitemsActivity::class.java).apply{
            }
            startActivity(intent);
        }
    }
    //method which binds the daya to view, it is late binding, as the value is not being assigned at the on-create of the activity
    private fun bindData() {
        lateBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        );
        var userObj = User();
        userObj.uName = "shashank";
        userObj.pwd ="12345";
        lateBinding.userModel = userObj;
    }
}