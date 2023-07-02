package com.prime.pantrymastergitcode

import javax.inject.Singleton

// ViewModel für globale Variablen
@Singleton
class MainViewModel {
    private var userID = "ABC"

    // UserID ist für den Login vorgesehen
    fun getUserID(): String{
        return userID
    }
    fun setUserID(userID: String) {
        this.userID = userID
    }
}
