package com.miguel.pokefan.APISERVER.Models

data class Pokemon(val count: Int, val next: String, val previous: String, val results: List<Results>)
data class Results(val name: String, val url: String )