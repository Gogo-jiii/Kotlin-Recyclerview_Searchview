package com.example.recyclerviewsearchview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*

class MyAdapter(context: MainActivity, arrayList: ArrayList<ModelClass>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>(), Filterable {

    private val context: Context
    private var arrayList: ArrayList<ModelClass>
    private lateinit var tempList: ArrayList<ModelClass>
    private var filteredList = ArrayList<ModelClass>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelClass = arrayList[position]
        holder.itemView.textView.text = modelClass.name
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private val searchFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            val query = constraint.toString()
            if (query.isEmpty()) {
                //tempList ha apla backup ahe. jevha search query empty hoil tevha hya backup madhun
                //original list baher kadhaychi which is the Full proof list.
                results.values = tempList
            } else {
                //filtered list la empty karaycha after each typed or deleted character and punha
                //filtered list loop laun populate karaychi as done below.
                filteredList.clear()
                for (item in tempList) {
                    if (item.name.lowercase().contains(query.lowercase().trim { it <= ' ' })) {
                        filteredList.add(item)
                    }
                }
                //finally arraylist ch publish hote, so filtered list arraylist madhe takaychi.
                results.values = filteredList
            }
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            this@MyAdapter.arrayList = results.values as ArrayList<ModelClass>
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView

        init {
            textView = itemView.findViewById(R.id.textView)
        }
    }

    init {
        this.context = context
        this.arrayList = arrayList
        tempList = arrayList
    }
}