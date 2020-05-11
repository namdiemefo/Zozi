package com.naemo.zozi.ui.account.bank

import android.app.Application
import android.text.TextUtils
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.naemo.zozi.R
import com.naemo.zozi.db.room.User
import com.naemo.zozi.db.room.UserRepository
import com.naemo.zozi.ui.base.BaseViewModel
import com.naemo.zozi.utils.AppUtils
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class BankViewModel(application: Application) : BaseViewModel<BankNavigator>(application) {

    var acc = ObservableField("")
    private var repository = UserRepository(application)

    fun next(name: String, bank: String) {
        val number = acc.get().toString()
        when {
            TextUtils.isEmpty(number) -> {
                getNavigator()?.showToast("enter account number")
            }
            TextUtils.isEmpty(bank) -> {
                getNavigator()?.showToast("enter name of bank")
            }
            else -> {
                val user = User(1, name, number, bank)
                saveUser(user)
                getNavigator()?.goToMain()
            }
        }
    }

    fun saveUser(user: User) {
        repository.saveUser(user)
    }
}

interface BankNavigator {

    fun showToast(msg: String)

    fun enter()

    fun goToMain()
}

@Module
class BankModule {

    @Provides
    fun providesBankViewModel(application: Application): BankViewModel {
        return BankViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_bank
    }
}