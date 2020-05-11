package com.naemo.zozi.ui.account.bank

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.naemo.zozi.BR
import com.naemo.zozi.R
import com.naemo.zozi.databinding.ActivityBankBinding
import com.naemo.zozi.ui.base.BaseActivity
import com.naemo.zozi.ui.main.MainActivity
import com.naemo.zozi.utils.AppUtils
import kotlinx.android.synthetic.main.activity_bank.*
import javax.inject.Inject

class BankActivity : BaseActivity<ActivityBankBinding, BankViewModel>(), BankNavigator {

    var bankViewModel: BankViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_bank
        @Inject set

    var appUtils = AppUtils()
        @Inject set

    var mBinder: ActivityBankBinding? = null

    internal var bank = arrayOf("--Select Bank--", "Zenith", "Gtb", "Access")
    private lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var bankSelected: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBinding()
        initView()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = bankViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    private fun initView() {
        arrayAdapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, bank) {
                override fun isEnabled(position: Int): Boolean {
                    return if (position == 0) {
                        return false
                    } else true
                }

                @TargetApi(Build.VERSION_CODES.M)
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    val typeface = ResourcesCompat.getFont(this@BankActivity, R.font.montserrat)
                    val tv = view as TextView
                    if (position == 0) {
                        tv.setTextColor(getColor(R.color.dot_light_screen1))
                        tv.setTypeface(typeface, Typeface.ITALIC)
                    } else {
                        tv.setTextColor(getColor(R.color.colorWhite))
                        tv.setTypeface(typeface, Typeface.ITALIC)
                    }
                    return view
                }

                @TargetApi(Build.VERSION_CODES.O)
                @RequiresApi(Build.VERSION_CODES.M)
                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    view.setBackgroundColor(resources.getColor(R.color.colorBackground))
                    val typeface = ResourcesCompat.getFont(this@BankActivity, R.font.montserrat)
                    val tv = view as TextView
                    if (position == 0) {
                        tv.setTextColor(getColor(R.color.colorHint))
                        tv.setTypeface(typeface, Typeface.ITALIC)
                    } else {
                        tv.setTextColor(getColor(R.color.dot_light_screen1))
                        tv.setTypeface(typeface, Typeface.ITALIC)

                    }
                    return view
                }
            }

        banks.adapter = arrayAdapter
        banks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    bankSelected = bank[p2]
                }

            }


    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): BankViewModel? {
        return bankViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun showToast(msg: String) {
        appUtils.showSnackBar(this, main_frane, msg)
    }

    override fun enter() {
        hideKeyBoard()
        val intents = intent
        val name = intents.getStringExtra("name")
        val bank = bankSelected
        getViewModel()?.next(name, bank)
    }

    override fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
