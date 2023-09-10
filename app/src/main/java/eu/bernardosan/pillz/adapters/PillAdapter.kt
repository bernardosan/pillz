package eu.bernardosan.pillz.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import eu.bernardosan.pillz.databinding.ItemPillBinding
import eu.bernardosan.pillz.model.ItemPill
import eu.bernardosan.pillz.model.PillModel



class PillAdapter(val context: Context, private val pillList: List<ItemPill>):RecyclerView.Adapter<PillAdapter.ItemViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class ItemViewHolder( private val binding: ItemPillBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bindItem(model: ItemPill){

            binding.tvName.text = model.name


            binding.tvQuantity.text =
                when(model.quantity){
                    1 -> "1 pill"
                    else -> "${model.quantity} pills"
                }

            binding.tvScheduled.text = "${model.time.split(":")[0]}:${model.time.split(":")[1]}"

            Glide
                .with(context)
                .load(eu.bernardosan.pillz.R.drawable.ic_pill_64)
                .into(binding.ivPill)


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            ItemPillBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(pillList[position])
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return pillList.size
    }

    interface  OnClickListener{
        fun onClick(position: Int)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }
}
