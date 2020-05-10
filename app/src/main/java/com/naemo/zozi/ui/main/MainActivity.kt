package com.naemo.zozi.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import com.hover.sdk.api.Hover
import com.naemo.zozi.BR
import com.naemo.zozi.R
import com.naemo.zozi.databinding.ActivityMainBinding
import com.naemo.zozi.db.room.User
import com.naemo.zozi.ui.base.BaseActivity
import java.net.URLEncoder
import java.util.*
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun shareLink() {
        val user = getViewModel()?.getUser()
        user?.observe(this, Observer {
            useUser(it)
        })
    }

    private fun useUser(it: User?) {
        val user = it?.toString()
       // val link = user?.toByte().toString()
        val enc = "UTF-8"
        val encode = URLEncoder.encode(user, enc)
      /*  val sendIntent : Intent = Intent().apply {
            Uri.parse(user)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, user)
            type = "text/plain"
        }*/
        val sendIntent : Intent = Uri.parse(user).let {
            Intent(Intent.EXTRA_TEXT, it)
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
