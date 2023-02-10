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
    val quitarQuerys = quitarRangoMenor.replace("&limit=20","")
    return quitarQuerys
}
fun obtenerRangoMenor(url:String): String {
    val quitarRangoMenor = url.replace("https://pokeapi.co/api/v2/pokemon?offset=","")
    val quitarQuerys = quitarRangoMenor.replace("&limit=20","")
    return quitarQuerys
}

