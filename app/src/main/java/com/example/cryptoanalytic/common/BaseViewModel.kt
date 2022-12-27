package com.example.cryptoanalytic.common

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

//    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
//    var error: MutableLiveData<String> = MutableLiveData("")
//    val oneTimeEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val TAG = this.javaClass.simpleName

}