package com.aquidigital.tipcalculator.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aquidigital.tipcalculator.R
import com.aquidigital.tipcalculator.databinding.SavedTipsListItemBinding
import com.aquidigital.tipcalculator.viewmodel.TipCalculationSummaryItem

class TipCalculationAdapter(val onItemSelected: (item: TipCalculationSummaryItem) -> Unit)
    : RecyclerView.Adapter<TipCalculationAdapter.TipSummaryViewHolder>() {

    private val tipCalculationSummaries = mutableListOf<TipCalculationSummaryItem>()

    fun updateList(updates: List<TipCalculationSummaryItem>) {
        tipCalculationSummaries.clear()
        tipCalculationSummaries.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<SavedTipsListItemBinding>(
            inflater, R.layout.saved_tips_list_item, parent, false)

       return TipSummaryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tipCalculationSummaries.count()
    }

    override fun onBindViewHolder(holderTipSummary: TipSummaryViewHolder, position: Int) {
        holderTipSummary.bind(tipCalculationSummaries[position])
    }

    inner class TipSummaryViewHolder (val binding: SavedTipsListItemBinding)
        : RecyclerView.ViewHolder(binding.root ) {

        fun bind(item: TipCalculationSummaryItem) {
            binding.item = item
            binding.root.setOnClickListener { onItemSelected(item) }
            binding.executePendingBindings()
        }
    }
}