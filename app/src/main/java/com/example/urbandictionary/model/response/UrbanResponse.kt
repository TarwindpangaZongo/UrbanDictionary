package com.example.urbandictionary.model.response

data class UrbanResponse(val list: MutableList<Word>)

data class Word(val definition: String, val thumbs_up: Int, val thumbs_down: Int)