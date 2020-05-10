package com.naemo.zozi.ui.account.bank

import android.app.Application
import com.naemo.zozi.R
import com.naemo.zozi.db.room.User
import com.naemo.zozi.db.room.UserRepository
import com.naemo.zozi.ui.base.BaseViewModel
import dagger.Module
import dagger.Provides

class BankViewModel(application: Application) : BaseViewModel<BankNavigator>(application) {

    private var repository = UserRepository(application)

    fun saveUser(user: User) {
        repository.saveUser(user)
    }
}

interface BankNavigator {


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