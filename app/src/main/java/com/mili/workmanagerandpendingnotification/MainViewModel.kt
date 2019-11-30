package com.mili.workmanagerandpendingnotification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val data = MutableLiveData<String>()
    val oneTimeWorkData = MutableLiveData<String>()
    private val time = MutableLiveData<Int>()
    private val oneTimeWorkDescription = MutableLiveData<String>()

    fun setPeriodicWorkButtonClicked() {
        time.value = data.value?.toInt()
    }

    fun setPeriodWork() : LiveData<Int> {
        return time
    }

    fun setOneTimeWorkButtonClicked() {
        oneTimeWorkDescription.value = oneTimeWorkData.value
    }

    fun setOneTimeWork() : LiveData<String> {
        return oneTimeWorkDescription
    }
}