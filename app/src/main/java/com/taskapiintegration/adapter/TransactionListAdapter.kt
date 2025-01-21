package com.taskapiintegration.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taskapiintegration.databinding.TransactionListItemBinding
import com.taskapiintegration.view.transactions.data.TransactionItem

class TransactionListAdapter(val context: Context) :
    RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {
    private var transactionItemList = ArrayList<TransactionItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransactionListItemBinding.inflate(inflater)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.root.layoutParams = lp
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionListAdapter.ViewHolder, position: Int) {
        holder.bind(transactionItemList[position])
    }
    fun getItem(index: Int): TransactionItem {
        return transactionItemList[index]
    }

    fun addItem(data: TransactionItem) {
        transactionItemList.add(data)
        val index = transactionItemList.indexOf(data)
        notifyItemInserted(index)
    }

    fun addAllItem(data: List<TransactionItem>) {
        transactionItemList.addAll(data)
        notifyDataSetChanged()
    }
    fun removeItem(index: Int) {
        transactionItemList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeAll() {
        transactionItemList.clear()
        notifyDataSetChanged()
    }

    fun replaceItem(index: Int, item: TransactionItem) {
        transactionItemList.set(index, item)
        notifyItemChanged(index)
    }

    fun removeItem(data: TransactionItem) {
        val index = transactionItemList.indexOf(data)
        transactionItemList.remove(data)
        notifyItemRemoved(index)
    }
    override fun getItemCount(): Int {
        return transactionItemList.size
    }
    inner class ViewHolder(private val binding: TransactionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionItem) {
            if (item.date.isNotEmpty()){
                binding.tvDate.text = "Date: " + item.date
            }
            if (item.category.isNotEmpty()){
                binding.tvCategory.text = "Category: " + item.category
            }
            if (item.amount.isNotEmpty()){
                binding.tvAmount.text = "Amount: " + item.amount
            }
            if (item.description.isNotEmpty()){
                binding.tvDescription.text = "Description: " + item.description
            }
        }
    }

}