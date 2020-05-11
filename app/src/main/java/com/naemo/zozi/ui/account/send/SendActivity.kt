package com.naemo.zozi.ui.account.send

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import com.naemo.zozi.BR
import com.naemo.zozi.R
import com.naemo.zozi.databinding.ActivitySendBinding
import com.naemo.zozi.db.room.User
import com.naemo.zozi.ui.base.BaseActivity
import com.naemo.zozi.utils.AppUtils
import kotlinx.android.synthetic.main.activity_send.*
import java.lang.Exception
import java.net.URLDecoder
import javax.inject.Inject


class SendActivity : BaseActivity<ActivitySendBinding, SendViewModel>(), SendNavigator {

    var sendViewModel: SendViewModel? = null
    @Inject set

    var mLayoutId = R.layout.activity_send
    @Inject set

    var mBinder : ActivitySendBinding? = null

    var appUtils = AppUtils()
        @Inject set

    private val PERMISSION_ID = 1

    private lateinit var name: String
    private lateinit var acc: String
    private lateinit var bank: String
    private lateinit var cash: String
    private lateinit var myBank: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBinding()
        if (checkPermissions()) {
            Hover.initialize(this)
        } else {
            requestPermissions()
        }

        handleIntent(intent)
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = sendViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SendViewModel? {
        return sendViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    private fun handleIntent(intent: Intent) {
       // val appLinkAction = intent.action
        val appLinkData = intent.data
        if (appLinkData != null) {
            val data = appLinkData.encodedQuery
            val myInfo = getViewModel()?.getUser()
            myInfo?.observe(this, Observer {
                useData(data, it)
            })

        }
    }

    private fun useData(data: String?, it: User) {
        myBank = it.bank
        val gson = Gson()
        val decode = URLDecoder.decode(data, "UTF-8")
        val user : User = gson.fromJson(decode, User::class.java)
        name = user.name
        acc = user.accNum
        bank = user.bank
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    override fun sendCash() {
        cash = amount.text.toString()
        if (TextUtils.isEmpty(cash)){
            appUtils.showSnackBar(this, send_frame, "enter amount")
        } else {
            showPopUp()
        }
    }

    private fun showPopUp() {
        AlertDialog.Builder(this)
            .setTitle("Confirm")
            .setMessage("Are you sure you want to send $cash N to $name ?")
            .setPositiveButton("Yes") {_, _  ->
                send()
            }
            .setNegativeButton("No") {_, _  -> }
            .setIcon(R.drawable.ic_warning)
            .show()
    }

    private fun send() {
        if (myBank == "Zenith" && bank == "Zenith") {
            sendZenith()
        } else if (myBank == "Gtb" && bank != "Gtb") {
            sendGtbOthers()
        } else if (myBank == "Access" && bank != "Access") {
            sendAccessOthers()
        } else {
            appUtils.showSnackBar(this, send_frame, "we don't support this feature yet")
        }

    }

    private fun sendAccessOthers() {
        val i : Intent = HoverParameters.Builder(this)
            .request("44340de0")
            .extra("amount", cash)
            .extra("accountNumber", acc)
            .extra("bank", bank)
            .buildIntent()

        startActivityForResult(i, 0)
    }

    private fun sendGtbOthers() {
        val i : Intent = HoverParameters.Builder(this)
            .request("100e69e2")
            .extra("amount", cash)
            .extra("accountNumber", acc)
            .extra("bank", bank)
            .buildIntent()

        startActivityForResult(i, 0)
    }

    private fun sendZenith() {
        try {
            val i : Intent = HoverParameters.Builder(this)
                .request("8cfc882f")
                .extra("amount", cash)
                .extra("accountNumber", acc)
                .buildIntent()

            startActivityForResult(i, 0)
        } catch (e: Exception) {
            appUtils.showSnackBar(this, send_frame, e.message.toString())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
         //   val sessionTextArr = data!!.getStringArrayExtra("session_messages")
          //  val uuid = data.getStringExtra("uuid")
            appUtils.showSnackBar(this, send_frame, "Success!")
        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Error: " + data!!.getStringExtra("error"), Toast.LENGTH_LONG)
                .show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Hover.initialize(this)
            }
        }
    }




}
