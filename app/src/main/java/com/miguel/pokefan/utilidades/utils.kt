package com.miguel.pokefan.utilidades

 fun obtenerNumero(url: String): String {
    /**
     * let number =str.match(/\d+/g); number.shift(); return number
     */
   // val patron = """/\d+/g""".toRegex()
   // val resultado = patron.matchEntire(url)
    //return resultado?.value.toString()
    val quitarurl = url.replace("https://pokeapi.co/api/v2/pokemon/","")
    val numero = quitarurl.replace("/","")
    return numero
}

fun imageAlternative(idPokemon: String): String {
    val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${idPokemon}.png"
    return url
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