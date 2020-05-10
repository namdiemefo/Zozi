package com.naemo.zozi.di


import com.naemo.zozi.ui.account.bank.BankActivity
import com.naemo.zozi.ui.account.bank.BankModule
import com.naemo.zozi.ui.main.MainActivity
import com.naemo.zozi.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [BankModule::class])
    abstract fun contributeBankActivity(): BankActivity
}