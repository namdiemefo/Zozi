package com.naemo.zozi.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.hover.sdk.api.Hover
import com.naemo.zozi.BR
import com.naemo.zozi.R
import com.naemo.zozi.databinding.ActivityMainBinding
import com.naemo.zozi.db.room.User
import com.naemo.zozi.ui.base.BaseActivity
import java.net.URLEncoder
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    var mainViewModel: MainViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_main
        @Inject set

    var mBinder : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBinding()
        Hover.initialize(this)

    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = mainViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)

    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MainViewModel? {
        return mainViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun sendCash() {
        Toast.makeText(this, "get a link", Toast.LENGTH_SHORT).show()
    }

    override fun shareLink() {
        val user = getViewModel()?.getUser()
        user?.observe(this, Observer {
            useUser(it)
        })
    }

    private fun useUser(it: User?) {
        val gson = Gson()
        val user = gson.toJson(it)
       // val user = it?.toString()
        val link = URLEncoder.encode(user, "UTF-8")
        Log.d("share", link.toString())

        val url = "http://www.zozi.com/send?$link"

        val sendIntent : Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
