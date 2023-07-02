package com.prime.pantrymastergitcode

import javax.inject.Singleton

@Singleton
class MainViewModel {
    private var userID = "ABC"

    fun getUserID(): String{
        return userID
    }
    fun setUserID(userID: String) {
        this.userID = userID
    }
}
