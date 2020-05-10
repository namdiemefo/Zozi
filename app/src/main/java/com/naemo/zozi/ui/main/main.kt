package com.naemo.zozi.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import com.naemo.zozi.R
import com.naemo.zozi.db.room.User
import com.naemo.zozi.db.room.UserRepository
import com.naemo.zozi.ui.base.BaseViewModel
import dagger.Module
import dagger.Provides

class MainViewModel(application: Application) : BaseViewModel<MainNavigator>(application) {

    private var repository = UserRepository(application)

    fun getUser(): LiveData<User>? {
        return repository.getUser()
    }
}

interface MainNavigator {

    fun sendCash()

    fun shareLink()
}

@Module
class MainModule {

    @Provides
    fun providesMainViewModel(application: Application): MainViewModel {
        return MainViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_main
    }
}