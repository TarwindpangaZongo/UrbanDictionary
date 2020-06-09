package com.example.urbandictionary.views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.R
import com.example.urbandictionary.injection.Injection
import com.example.urbandictionary.model.response.Word
import com.example.urbandictionary.viewmodels.UrbanViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val urbanViewModel: UrbanViewModel by viewModel()

    private var termsAdapter: TermsAdapter = TermsAdapter()
    private val injection = Injection()
    private var listOfItems = arrayOf("Thumb Up", "Thumb Down")
    private var termSpinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        termSpinner = this.sort_spinner
        termSpinner!!.onItemSelectedListener = this
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItems)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        termSpinner!!.adapter = arrayAdapter

        urbanViewModel.stateLiveData.observe(this, Observer { appState ->
            when (appState) {
                is UrbanViewModel.AppState.LOADING -> displayLoading()
                is UrbanViewModel.AppState.SUCCESS -> displayTerms(appState.termsList)
                is UrbanViewModel.AppState.ERROR -> displayMessage(appState.message)
                else -> displayMessage(getString(R.string.something_went_wrong))
            }
        })

        initRecyclerView()
        termSearch()
    }

    private fun displayTerms(termsList: MutableList<Word>) {
        // set recycler to eliminate flicker
        termsAdapter.update(termsList)

        // set correct visible element
        progressBar.visibility = View.GONE
        termRV.visibility = View.VISIBLE
        messageText.visibility = View.GONE
        termSpinner?.setSelection(0)
    }

    private fun displayLoading() {
        // set correct visible element
        progressBar.visibility = View.VISIBLE
        termRV.visibility = View.GONE
        messageText.visibility = View.GONE
    }

    private fun displayMessage(message: String) {
        // set correct visible element
        progressBar.visibility = View.GONE
        termRV.visibility = View.GONE
        messageText.visibility = View.VISIBLE
        //set message
        messageText.text = message
    }

    private fun initRecyclerView() {
        termRV.layoutManager = LinearLayoutManager(this)
        termRV.adapter = termsAdapter
    }

    private fun termSearch() {
        urbanViewModel.searchDefinitions(term_search)

    }

    override fun onItemSelected(adapterView: AdapterView<*>, viea: View, position: Int, id: Long) {
        if (position == 0) {
            termsAdapter.sortByThumbsUp()
        } else if (position == 1) {
            termsAdapter.sortByThumbsDown()
        }
    }

    override fun onNothingSelected(adaterView: AdapterView<*>) {
        termsAdapter.sortByThumbsUp()
    }
}
