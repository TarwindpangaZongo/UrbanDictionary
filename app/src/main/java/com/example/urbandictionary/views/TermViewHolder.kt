package com.example.urbandictionary.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.model.response.Word
import kotlinx.android.synthetic.main.term_item.view.*

class TermViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(term: Word) {
        //populate text
        itemView.definition.text = term.definition
        itemView.uptxt.text = term.thumbs_up.toString()
        itemView.downtxt.text = term.thumbs_down.toString()
    }
}