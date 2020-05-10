package com.naemo.zozi.ui.account.name

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.naemo.zozi.R
import com.naemo.zozi.db.pref.PrefManager
import com.naemo.zozi.ui.account.bank.BankActivity
import com.naemo.zozi.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_name.*
import javax.inject.Inject

class NameActivity : AppCompatActivity() {

    var pref = PrefManager(this)
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!pref.isFirstRun()) {
            launchMainScreen()
            finish()
        }

        setContentView(R.layout.activity_name)
    }

    private fun launchMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun enterName(view: View) {
        val userName = name.text.toString()
        val intent = Intent(this, BankActivity::class.java)
        intent.putExtra("name", userName)
        startActivity(intent)
    }
}
