package eu.bernardosan.pillz.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainViewModel(calendar: Calendar) : ViewModel()  {

    val _date = calendar.time
    val _dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val _dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val _hours = calendar.get(Calendar.HOUR_OF_DAY)
    val _minutes = calendar.get(Calendar.MINUTE)

    // Formatar a data
    val dateFormater = SimpleDateFormat("dd/MM/yyyy")
    val dateString = dateFormater.format(_date)

    val dayOfMonth = MutableLiveData<Int>().apply {
        value = _dayOfMonth
    }

    val dayOfWeek = MutableLiveData<Int>().apply {
        value = _dayOfWeek
    }

    val dayOfHour = MutableLiveData<Int>().apply {
        value = _hours
    }

    val dayOfMinutes = MutableLiveData<Int>().apply {
        value = _minutes
    }



}