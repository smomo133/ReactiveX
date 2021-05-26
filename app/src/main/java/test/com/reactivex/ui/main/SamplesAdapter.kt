package test.com.reactivex.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import test.com.reactivex.R
import test.com.reactivex.data.Example

class SamplesAdapter(private val onClick: (Example) -> Unit):
    ListAdapter<Example, SamplesAdapter.SampleViewHolder>(SampleDiffCallback) {

    class SampleViewHolder(itemView: View, val onClick:(Example)->Unit):
        RecyclerView.ViewHolder(itemView){
        private val sampleTextView:TextView = itemView.findViewById(R.id.sample_text)
        private var currentSample:Example? = null

        init {
            itemView.setOnClickListener {
                currentSample?.let {
                    onClick(it)
                }
            }
        }

        fun bind(sample:Example){
            currentSample = sample
            sampleTextView.text = sample.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_item, parent, false)
        return SampleViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        val sample = getItem(position)
        holder.bind(sample)
    }
}

object SampleDiffCallback:DiffUtil.ItemCallback<Example>(){
    override fun areItemsTheSame(oldItem: Example, newItem: Example): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Example, newItem: Example): Boolean {
        return oldItem.id == newItem.id
    }
}