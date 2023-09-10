package eu.bernardosan.pillz.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.bernardosan.pillz.model.DayModel
import eu.bernardosan.pillz.model.ItemPill
import eu.bernardosan.pillz.model.PillModel
import eu.bernardosan.pillz.model.User
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {

    private var _count = 0
    private var _calendar = Calendar.getInstance()
    private var _year = _calendar.get(Calendar.YEAR)
    private var _month = _calendar.get(Calendar.MONTH)
    private var _day = _calendar.get(Calendar.DAY_OF_MONTH)
    private val _user =
        User("Bernardo",
            "santiaguinh@gmail.com",
            arrayListOf(
                PillModel("Creatine", 2, 0, 1694172600000, null, listOf(Calendar.SUNDAY,Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY,Calendar.SATURDAY), "8:00"),
                PillModel("Creatine", 2, 0, 1692072600000, null, listOf(Calendar.SUNDAY,Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY,Calendar.SATURDAY), "5:00"),
                PillModel("Loratadine", 1, 8, 1694172600000, 1696073400000, null, null)
            )
        )


    val count = MutableLiveData<Int>().apply {
        value = _count
    }

    val calendar = MutableLiveData<Calendar>().apply {
        value = _calendar
    }

    val year = MutableLiveData<Int>().apply {
        value = _year
    }

    val month = MutableLiveData<Int>().apply {
        value = _month
    }

    val day = MutableLiveData<Int>().apply {
        value = _day
    }

    val user = MutableLiveData<User>().apply {
        value = _user
    }

    private var _dayList = generateDayItems()

    val dayList = MutableLiveData<List<DayModel>>().apply {
        value = _dayList
    }

    fun addCounter(){
        _count++
        count.value = _count
    }



    fun generateDayItems(): List<DayModel> {
        calendar.value!!.set(year.value!!, month.value!!, 1)
        val daysInMonth = calendar.value!!.getActualMaximum(Calendar.DAY_OF_MONTH)


        val dayItems = mutableListOf<DayModel>()
        for (dayOfMonth in 1..daysInMonth) {
            calendar.value!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dayOfWeek = when (calendar.value!!.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SUNDAY -> "SUN"
                Calendar.MONDAY -> "MON"
                Calendar.TUESDAY -> "TUE"
                Calendar.WEDNESDAY -> "WED"
                Calendar.THURSDAY -> "THU"
                Calendar.FRIDAY -> "FRI"
                Calendar.SATURDAY -> "SAT"
                else -> ""
            }

            val listItemPill = ArrayList<ItemPill>()

            user.value!!.pillsList.forEach {

                it.startingDate
                val startCalendarPill = Calendar.getInstance()
                startCalendarPill.timeInMillis = it.startingDate!!


                val finishCalendarPill = Calendar.getInstance()
                if(it.finishingDate != null) {
                    finishCalendarPill.timeInMillis = it.finishingDate
                } else
                    finishCalendarPill.timeInMillis = 0

                if(
                    it.periodicity == 0 &&
                    dayOfMonth >= startCalendarPill.get(Calendar.DAY_OF_MONTH) &&
                    it.daysOfWeek!!.contains(calendar.value!!.get(Calendar.DAY_OF_WEEK))
                ){
                    val itemPill = ItemPill(
                        "",
                        it.name,
                        it.quantity,
                        it.timeOfDay!!
                    )
                    listItemPill.add(itemPill)
                }


                if(
                    it.periodicity > 0 &&
                    dayOfMonth >= startCalendarPill.get(Calendar.DAY_OF_MONTH) &&
                    dayOfMonth <= finishCalendarPill.get(Calendar.DAY_OF_MONTH)
                ){
                    Log.i("day", calendar.value!!.get(Calendar.DAY_OF_MONTH).toString() + " " + calendar.value!!.timeInMillis)
                    val pills = calculatePillSchedule(it, calendar.value!!.timeInMillis)
                    pills.forEach {
                        listItemPill.add(it)
                    }
                }
            }


            listItemPill.sortBy { pill ->
                val time = pill.time
                val timeParts = time.split(":")
                val hours = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
                val minutes = timeParts.getOrNull(1)?.toIntOrNull() ?: 0
                hours * 60 + minutes // Convert HH:mm to minutes for sorting
            }


            val dayItem = DayModel(dayOfMonth, dayOfWeek, month.value!!, calendar.value!!.get(Calendar.YEAR), listItemPill)
            dayItems.add(dayItem)
        }
        return dayItems
    }

    private fun calculatePillSchedule(pill: PillModel, actualDate: Long): ArrayList<ItemPill> {
        val itemPills = ArrayList<ItemPill>()

        val startingTime = pill.startingDate!!
        val finishingTime = pill.finishingDate!!

        // Calculate the time interval in milliseconds based on periodicity
        val timeIntervalMillis = pill.periodicity.toLong() * 60 * 60 * 1000 // hours to milliseconds

        // Initialize the time for the first pill
        var pillTime = startingTime

        // Ensure the current time is within the date range
        val startDateInMillis = actualDate
        val endDateInMillis = actualDate + 24 * 60 * 60 * 1000 // 24 hours later

        // Generate pills until the finishing time is reached
        while (pillTime <= finishingTime) {
            // Check if the pill time is within the current day
            if (pillTime >= startDateInMillis && pillTime <= endDateInMillis) {
                itemPills.add(
                    ItemPill(
                        pill.image,
                        pill.name,
                        pill.quantity,
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(pillTime)
                    )
                )
            }

            // Increment the pill time by the time interval
            pillTime += timeIntervalMillis
        }

        return itemPills
    }





}