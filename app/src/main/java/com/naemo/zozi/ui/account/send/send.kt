package com.naemo.zozi.ui.account.send

import android.app.Application
import androidx.lifecycle.LiveData
import com.naemo.zozi.R
import com.naemo.zozi.db.room.User
import com.naemo.zozi.db.room.UserRepository
import com.naemo.zozi.ui.base.BaseViewModel
import dagger.Module
import dagger.Provides

class SendViewModel(application: Application) : BaseViewModel<SendNavigator>(application) {

    private var repository = UserRepository(application)

    fun getUser(): LiveData<User>? {
        return repository.getUser()
    }
}

interface SendNavigator {

    fun sendCash()

}

@Module
class SendModule {

    @Provides
    fun providesBankViewModel(application: Application): SendViewModel {
        return SendViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_send
    }
}