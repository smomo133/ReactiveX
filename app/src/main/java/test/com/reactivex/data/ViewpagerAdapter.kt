package test.com.reactivex.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import test.com.reactivex.R

class ViewpagerAdapter(private val bgColors:List<Int>):RecyclerView.Adapter<ViewpagerHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewpagerHolder =
                ViewpagerHolder(LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false))

        override fun onBindViewHolder(holder: ViewpagerHolder, position: Int) {
                holder.bind(bgColors[position], position)
        }

        override fun getItemCount(): Int = bgColors.size
}

class ViewpagerHolder internal constructor(private val item: View) :
        RecyclerView.ViewHolder(item){
        private val textView = item.findViewById<TextView>(R.id.page_name)
        fun bind(@ColorRes bgColor:Int, position:Int){
                textView.text = "Page $position"
                item.setBackgroundColor(ContextCompat.getColor(item.context, bgColor))
        }
}