package com.taskapiintegration.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.taskapiintegration.databinding.TransactionListItemBinding
import com.taskapiintegration.view.transactions.data.TransactionItem
import java.util.*
import kotlin.collections.ArrayList

class TransactionListAdapter(val context: Context) :
    RecyclerView.Adapter<TransactionListAdapter.ViewHolder>(), Filterable {

    private var transactionItemList = ArrayList<TransactionItem>()
    private var filteredTransactionList = ArrayList<TransactionItem>() // For filtering

    init {
        filteredTransactionList = transactionItemList // Initialize with full list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransactionListItemBinding.inflate(inflater, parent, false) // Pass parent
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionListAdapter.ViewHolder, position: Int) {
        holder.bind(filteredTransactionList[position]) // Use filtered list
    }

    fun getItem(index: Int): TransactionItem {
        return filteredTransactionList[index]
    }

    fun addItem(data: TransactionItem) {
        transactionItemList.add(data)
        filteredTransactionList.add(data)
        notifyItemInserted(filteredTransactionList.size - 1)
    }

    fun addAllItem(data: List<TransactionItem>) {
        transactionItemList.addAll(data)
        filteredTransactionList = ArrayList(transactionItemList) // Update filtered list
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        val item = filteredTransactionList[index]
        transactionItemList.remove(item)
        filteredTransactionList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeAll() {
        transactionItemList.clear()
        filteredTransactionList.clear()
        notifyDataSetChanged()
    }

    fun replaceItem(index: Int, item: TransactionItem) {
        transactionItemList[index] = item
        filteredTransactionList[index] = item
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int {
        return filteredTransactionList.size // Use filtered list
    }

    inner class ViewHolder(private val binding: TransactionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionItem) {
            binding.tvDate.text = "Date: ${item.date}"
            binding.tvCategory.text = "Category: ${item.category}"
            binding.tvAmount.text = "Amount: ${item.amount}"
            binding.tvDescription.text = "Description: ${item.description}"
        }
    }

    // Implement Filterable
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault())?.trim()

                val filteredList = if (query.isNullOrEmpty()) {
                    transactionItemList // Show full list if query is empty
                } else {
                    transactionItemList.filter {
                        it.date.lowercase(Locale.getDefault()).contains(query) ||
                                it.category.lowercase(Locale.getDefault()).contains(query) ||
                                it.amount.lowercase(Locale.getDefault()).contains(query) ||
                                it.description.lowercase(Locale.getDefault()).contains(query)
                    } as ArrayList<TransactionItem>
                }

                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredTransactionList = results?.values as ArrayList<TransactionItem>
                notifyDataSetChanged()
            }
        }
    }
}
