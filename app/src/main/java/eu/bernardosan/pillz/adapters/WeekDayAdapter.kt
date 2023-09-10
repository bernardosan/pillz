package eu.bernardosan.pillz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import eu.bernardosan.pillz.databinding.ItemWeekDayBinding
import kotlin.collections.ArrayList


class WeekDayAdapter(private val daysList: List<Int> = listOf(0,1,2,3,4,5,6)):RecyclerView.Adapter<WeekDayAdapter.ItemViewHolder>() {

    private var onClickListener: OnClickListener? = null

    private var selectedItemsArray =  ArrayList<Int>()


    inner class ItemViewHolder( val binding: ItemWeekDayBinding): RecyclerView.ViewHolder(binding.root){

        fun bindItem(position: Int){

            when(position){
                0-> binding.tvDayOfWeek.text = "Mon"
                1-> binding.tvDayOfWeek.text = "Tue"
                2-> binding.tvDayOfWeek.text = "Wed"
                3-> binding.tvDayOfWeek.text = "Thu"
                4-> binding.tvDayOfWeek.text = "Fri"
                5-> binding.tvDayOfWeek.text = "Sat"
                6-> binding.tvDayOfWeek.text = "Sun"
            }

            binding.llWeekDayItem.isSelected = selectedItemsArray.contains(position)
            binding.ivCheckIcon.isSelected = selectedItemsArray.contains(position)


            //binding.llWeekDayItem.isSelected =
             //   model.dayOfMonth == homeViewModel.day.value

            //binding.llWeekDayItem.isActivated = (model.dayOfMonth != homeViewModel.day.value &&
            //    model.dayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemViewHolder(ItemWeekDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        //val layoutParams =
        //RecyclerView.LayoutParams((parent.width * (1/7)).toInt(), RecyclerView.LayoutParams.WRAP_CONTENT)

        //layoutParams.setMargins((15.toDp()).toPx(),0,(40.toDp()).toPx(),0)

        //view.binding.root.layoutParams = layoutParams

        return view
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.bindItem(position)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position)

                if(selectedItemsArray.contains(position))
                {
                    selectedItemsArray.remove(position)
                    holder.binding.llWeekDayItem.isSelected = false
                    notifyItemChanged(position)
                } else {
                    selectedItemsArray.add(position)
                    holder.binding.llWeekDayItem.isSelected = true
                    notifyItemChanged(position)
                }
            }
        }
    }


    interface  OnClickListener{
        fun onClick(position: Int)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    fun getWeekList(): ArrayList<Int>{
        return selectedItemsArray
    }

    override fun getItemCount(): Int {
        return daysList.size
    }
}
