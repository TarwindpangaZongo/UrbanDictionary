package com.example.urbandictionary.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.R
import com.example.urbandictionary.model.response.Word

class TermsAdapter : RecyclerView.Adapter<TermViewHolder>() {

    private var terms: MutableList<Word> = mutableListOf()

    override fun getItemCount() = terms.size

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        holder.bindItem(terms[position])
    }

    fun update(terms: MutableList<Word>) {
        this.terms.clear()
        this.terms = terms
        this.terms.sortByDescending {
            it.thumbs_up
        }
        notifyDataSetChanged()
    }

    fun sortByThumbsUp() {
        this.terms.sortByDescending {
            it.thumbs_up
        }
        notifyDataSetChanged()
    }

    fun sortByThumbsDown() {
        this.terms.sortByDescending {
            it.thumbs_down
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder =
        TermViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.term_item,
                parent,
                false
            )
        )
}