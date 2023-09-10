package eu.bernardosan.pillz.adapters

import android.app.Activity
import android.content.Context
import android.content.ReceiverCallNotAllowedException
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import eu.bernardosan.pillz.R
import eu.bernardosan.pillz.databinding.ItemCalendarDayBinding
import eu.bernardosan.pillz.model.DayModel
import eu.bernardosan.pillz.viewmodel.HomeViewModel
import java.time.LocalDate
import java.util.*


class DayCalendarAdapter(val homeViewModel: HomeViewModel):RecyclerView.Adapter<DayCalendarAdapter.ItemViewHolder>() {

    private var onClickListener: OnClickListener? = null


    inner class ItemViewHolder( private val binding: ItemCalendarDayBinding): RecyclerView.ViewHolder(binding.root){

        fun bindItem(model: DayModel){

            binding.tvDayOfWeek.text = model.dayOfWeek
            binding.tvDayOfMonth.text = model.dayOfMonth.toString()

            binding.llCalendarDayItem.isSelected =
                model.dayOfMonth == homeViewModel.day.value

            binding.llCalendarDayItem.isActivated = (model.month == Calendar.getInstance().get(Calendar.MONTH) && model.dayOfMonth != homeViewModel.day.value &&
                model.dayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            ItemCalendarDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.bindItem(homeViewModel.dayList.value!![position])

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position)
            }
        }
    }


    override fun getItemCount(): Int {
        return homeViewModel.dayList.value!!.size
    }

    interface  OnClickListener{
        fun onClick(position: Int)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    fun updateList(list: List<DayModel>) {
        homeViewModel.dayList.value = list
        notifyDataSetChanged()
    }
}
