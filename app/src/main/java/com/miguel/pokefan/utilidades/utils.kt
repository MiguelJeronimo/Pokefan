package com.miguel.pokefan.utilidades

fun obtenerNumero(url: String): String {
    /**
     * let number =str.match(/\d+/g); number.shift(); return number
     */
    val quitarurl = url.replace("https://pokeapi.co/api/v2/pokemon/","")
    val numero = quitarurl.replace("/","")
    return numero
}

//https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20
fun obtenerRangoMayor(url: String): String {
    val quitarRangoMenor = url.replace("https://pokeapi.co/api/v2/pokemon?offset=","")
    val quitarQuerys = quitarRangoMenor.replace("&limit=50","")
    return quitarQuerys
}
fun obtenerRangoMenor(url:String): String {
    val quitarRangoMenor = url.replace("https://pokeapi.co/api/v2/pokemon?offset=","")
    val quitarQuerys = quitarRangoMenor.replace("&limit=50","")
    return quitarQuerys
}

fun getLimit(url:String, rank: String): String {
    val limitRegex = Regex("limit=(\\d+)")
    val matchResult = limitRegex.find(url)
    return matchResult?.groups?.get(1)?.value.toString()
}

