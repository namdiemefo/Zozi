package com.naemo.zozi.ui.account.bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.naemo.zozi.BR
import com.naemo.zozi.R
import com.naemo.zozi.databinding.ActivityBankBinding
import com.naemo.zozi.db.room.User
import com.naemo.zozi.db.room.UserRepository
import com.naemo.zozi.ui.base.BaseActivity
import com.naemo.zozi.ui.main.MainActivity
import com.naemo.zozi.ui.main.MainViewModel
import kotlinx.android.synthetic.main.activity_bank.*
import javax.inject.Inject

class BankActivity : BaseActivity<ActivityBankBinding, BankViewModel>(), BankNavigator {

    var bankViewModel: BankViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_bank
        @Inject set

    var mBinder: ActivityBankBinding? = null

    internal var bank = arrayOf("--Select Bank--", "Zenith", "Gtb", "Access", "Ecobank", "Uba")
    private lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var bankSelected: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_bank)
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

    fun enterBank(view: View) {
        val intents = intent
        val name = intents.getStringExtra("name")
        val bank = bankSelected
        val account = acc_number.text.toString()
        val user = User(1, name, account, bank)
        getViewModel()?.saveUser(user)
        startActivity(Intent(this, MainActivity::class.java))
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
}
