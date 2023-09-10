package eu.bernardosan.pillz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddPageViewModel : ViewModel() {

    private var _count = 0

    val count = MutableLiveData<Int>().apply {
        value = _count
    }

    fun addCounter(){
        _count++
    }
}