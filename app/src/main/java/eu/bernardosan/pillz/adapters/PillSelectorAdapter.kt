package eu.bernardosan.pillz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.bernardosan.pillz.databinding.ItemPillSelectorBinding
import eu.bernardosan.pillz.model.PillSelectorModel
import java.util.*


class PillSelectorAdapter(private val introList: List<PillSelectorModel>):RecyclerView.Adapter<PillSelectorAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemPillSelectorBinding): RecyclerView.ViewHolder(binding.root){

        fun bindItem(model: PillSelectorModel){
            binding.titleTv.text = model.title
            binding.iconIv.setImageResource(model.image)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPillSelectorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(introList[position])
    }



    override fun getItemCount(): Int {
        return introList.size
    }
}
